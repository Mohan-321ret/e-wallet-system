package com.e_wallet_system.backend.controller;

import com.e_wallet_system.backend.models.User;
import com.e_wallet_system.backend.models.wallet;
import com.e_wallet_system.backend.repository.UserRepo;
import com.e_wallet_system.backend.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private WalletRepo walletRepo;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/clear-users")
    public ResponseEntity<?> clearAllUsers() {
        userRepo.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }
    
    @GetMapping("/wallets")
    public ResponseEntity<?> getAllWallets() {
        List<wallet> wallets = walletRepo.findAll();
        return ResponseEntity.ok(wallets);
    }
    
    @GetMapping("/wallets/{email}")
    public ResponseEntity<?> getWalletsByEmail(@PathVariable String email) {
        List<wallet> wallets = walletRepo.findByEmail(email);
        return ResponseEntity.ok(wallets);
    }
    
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}