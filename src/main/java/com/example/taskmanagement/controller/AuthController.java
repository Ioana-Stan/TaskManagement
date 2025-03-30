package com.example.taskmanagement.controller;

import com.example.taskmanagement.domain.authentication.JwtUtil;
import com.example.taskmanagement.domain.model.OwnerDTO;
import com.example.taskmanagement.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OwnerService ownerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(OwnerService ownerService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.ownerService = ownerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        ownerService.createOwner(name, email, encodedPassword);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        OwnerDTO owner = ownerService.getOwnerByEmail(email);

        if (owner == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (passwordEncoder.matches(password, owner.getPassword())) {
            String token = jwtUtil.generateToken(owner.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT is stateless — just return 200 OK
        return ResponseEntity.ok("Logged out (client must discard token)");
    }
}

