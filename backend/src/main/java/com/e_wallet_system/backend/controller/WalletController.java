package com.e_wallet_system.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e_wallet_system.backend.models.Transaction;
import com.e_wallet_system.backend.models.wallet;
import com.e_wallet_system.backend.service.WalletService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allows frontend React app to call backend
public class WalletController {

    @Autowired
    private WalletService walletService;

    // ✅ Endpoint 1: Add Wallet
    // @PostMapping("/addWallet")
    // public String addWallet(@RequestBody wallet wallet) {
    //     walletService.addWallet(wallet);
    //     return "Wallet created successfully!";
    // }
    @PostMapping("/addWallet")
public ResponseEntity<String> addWallet(@RequestBody wallet wallet) {
    try {
        walletService.addWallet(wallet);
        return ResponseEntity.ok("Wallet created successfully!");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error creating wallet: " + e.getMessage());
    }
}


    // ✅ Endpoint 2: Transfer Funds
    // @PostMapping("/transferFunds")
    // public String transferFunds(@RequestBody Map<String, Object> payload) {
    //     int fromWalletId = (int) payload.get("fromWalletId");
    //     int toWalletId = (int) payload.get("toWalletId");
    //     double amount = Double.parseDouble(payload.get("amount").toString());

    //     return walletService.transferFunds(fromWalletId, toWalletId, amount);
    // }
    @PostMapping("/transferFunds")
public ResponseEntity<String> transferFunds(@RequestBody Map<String, Object> request) {
    try {
        int fromWalletId = Integer.parseInt(request.get("fromWalletId").toString());
        int toWalletId = Integer.parseInt(request.get("toWalletId").toString());
        double amount = Double.parseDouble(request.get("amount").toString());

        walletService.transferFunds(fromWalletId, toWalletId, amount);
        return ResponseEntity.ok("Transfer successful!");
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("Invalid number format.");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error processing transfer: " + e.getMessage());
    }
}


    // ✅ Endpoint 3: Get All Transactions
    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions() {
        return walletService.getAllTransactions();
    }

     // ✅ Endpoint 4: TopUp Wallet
  @PostMapping("/topUp")
public ResponseEntity<String> topUpWallet(@RequestBody Map<String, Object> request) {
    try {
        int walletId = Integer.parseInt(request.get("walletId").toString());
        double amount = Double.parseDouble(request.get("amount").toString());

        walletService.topUpWallet(walletId, amount);
        return ResponseEntity.ok("Wallet topped up successfully!");
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("Invalid number format.");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError()
                             .body("Error topping up wallet: " + e.getMessage());
    }
}

    // ✅ Endpoint 5: Get Wallets by Email
    @GetMapping("/getWalletsByEmail/{email}")
    public List<wallet> getWalletsByEmail(@PathVariable String email) {
        return walletService.getWalletsByEmail(email);
    }
}
