package com.e_wallet_system.backend.controller;

import com.e_wallet_system.backend.models.User;
import com.e_wallet_system.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/create-admin")
    public ResponseEntity<?> createAdmin() {
        // Check if admin already exists
        if (userRepo.findByEmail("admin@wallet").isPresent()) {
            return ResponseEntity.ok("Admin already exists! Email: admin@wallet, Password: admin123");
        }

        // Create admin user
        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@wallet");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        
        userRepo.save(admin);
        return ResponseEntity.ok("Admin created successfully! Email: admin@wallet, Password: admin123");
    }
}

@Component
class AdminSetup implements CommandLineRunner {
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findByEmail("admin@wallet").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@wallet");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepo.save(admin);
            System.out.println("✅ Admin user created: admin@wallet / admin123");
        }
    }
}