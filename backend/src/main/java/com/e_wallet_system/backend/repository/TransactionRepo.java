package com.e_wallet_system.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_wallet_system.backend.models.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
}
