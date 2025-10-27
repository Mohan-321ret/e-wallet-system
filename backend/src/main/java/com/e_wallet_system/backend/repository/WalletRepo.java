package com.e_wallet_system.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_wallet_system.backend.models.wallet;

@Repository
public interface WalletRepo extends JpaRepository<wallet, Integer> {
    List<wallet> findByEmail(String email);
}
