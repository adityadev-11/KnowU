package com.knowu.controller;

import com.knowu.dto.AuthDTO;
import com.knowu.model.User;
import com.knowu.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDTO.RegisterRequest req) {
        if (req.getEmail() == null || req.getEmail().isBlank()
                || req.getPassword() == null || req.getPassword().isBlank()
                || req.getName() == null || req.getName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new AuthDTO.ErrorResponse("Name, email and password are required"));
        }

        if (userRepository.existsByEmail(req.getEmail().toLowerCase().trim())) {
            return ResponseEntity.badRequest()
                    .body(new AuthDTO.ErrorResponse("An account with this email already exists"));
        }

        if (req.getPassword().length() < 6) {
            return ResponseEntity.badRequest()
                    .body(new AuthDTO.ErrorResponse("Password must be at least 6 characters"));
        }

        User user = new User(
                req.getName().trim(),
                req.getEmail().toLowerCase().trim(),
                passwordEncoder.encode(req.getPassword()),
                User.Role.USER
        );
        user = userRepository.save(user);

        return ResponseEntity.status(201).body(new AuthDTO.AuthResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getRole().name(), "Registration successful"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest req) {
        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthDTO.ErrorResponse("Email and password are required"));
        }

        var userOpt = userRepository.findByEmail(req.getEmail().toLowerCase().trim());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(new AuthDTO.ErrorResponse("Invalid email or password"));
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401)
                    .body(new AuthDTO.ErrorResponse("Invalid email or password"));
        }

        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getRole().name(), "Login successful"
        ));
    }
}
