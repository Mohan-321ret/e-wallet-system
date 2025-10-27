package com.e_wallet_system.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_wallet_system.backend.models.Transaction;
import com.e_wallet_system.backend.models.wallet;
import com.e_wallet_system.backend.repository.TransactionRepo;
import com.e_wallet_system.backend.repository.WalletRepo;

@Service
public class WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    // ✅ Create a new wallet
    public wallet addWallet(wallet wallet) {
        // Validation: phone must be 10 digits
        if (!wallet.getPhone().matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        // Validation: balance must be positive
        if (wallet.getBalance() < 0) {
            throw new IllegalArgumentException("Initial balance must be positive");
        }

        return walletRepo.save(wallet);
    }

    // ✅ Transfer funds between wallets
    public String transferFunds(int fromWalletId, int toWalletId, double amount) {
        Optional<wallet> fromWalletOpt = walletRepo.findById(fromWalletId);
        Optional<wallet> toWalletOpt = walletRepo.findById(toWalletId);

        if (fromWalletOpt.isEmpty() || toWalletOpt.isEmpty()) {
            throw new IllegalArgumentException("One or both wallets not found");
        }

        wallet fromWallet = fromWalletOpt.get();
        wallet toWallet = toWalletOpt.get();

        // Validation: amount positive
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // Validation: sufficient balance
        if (fromWallet.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Deduct and add balance
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        // Save updated wallets
        walletRepo.save(fromWallet);
        walletRepo.save(toWallet);

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setFromWalletId(fromWalletId);
        transaction.setToWalletId(toWalletId);
        transaction.setAmount(amount);
        transactionRepo.save(transaction);

        return "Transfer successful!";
    }

    // ✅ Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    //topup wallet from bank account 
    public void topUpWallet(int walletId, double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive.");
    }

    wallet wallet = walletRepo.findById(walletId)
            .orElseThrow(() -> new IllegalArgumentException("Wallet not found with ID: " + walletId));

    wallet.setBalance(wallet.getBalance() + amount);
    walletRepo.save(wallet);
}

    // ✅ Get wallets by email
    public List<wallet> getWalletsByEmail(String email) {
        return walletRepo.findByEmail(email);
    }
}
