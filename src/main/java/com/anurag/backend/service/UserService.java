package com.anurag.backend.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anurag.backend.dto.AuthResponse;
import com.anurag.backend.dto.LoginRequest;
import com.anurag.backend.dto.RegisterRequest;
import com.anurag.backend.entity.User;
import com.anurag.backend.repository.UserRepository;

/**
 * Service Class: UserService
 * Handles user registration, credential validation, SHA-256 password hashing, and authentication.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. User Registration
    public AuthResponse register(RegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Registration details cannot be empty.");
        }
        if (request.getUsername() == null || request.getUsername().trim().length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (request.getPassword() == null || request.getPassword().trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }

        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' is already taken.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is already registered.");
        }

        String hashedPassword = hashPassword(request.getPassword().trim());
        String fullName = request.getFullName() != null ? request.getFullName().trim() : username;

        User user = new User(username, email, hashedPassword, fullName, "USER");
        User savedUser = userRepository.save(user);

        return new AuthResponse(
            true,
            "Registration successful",
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getFullName(),
            savedUser.getRole()
        );
    }

    // 2. User Login
    public AuthResponse login(LoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Login credentials cannot be empty.");
        }
        String identifier = request.getUsernameOrEmail();
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Username or email is required.");
        }
        identifier = identifier.trim();

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }
        String password = request.getPassword().trim();

        // Find user by username, email, or full name (case-insensitive)
        java.util.List<User> userList = userRepository.findByUsernameOrEmailIgnoreCase(identifier);
        if (userList == null || userList.isEmpty()) {
            userList = userRepository.findByIdentifierIgnoreCase(identifier);
        }

        if (userList == null || userList.isEmpty()) {
            throw new IllegalArgumentException("No account found with username or email '" + identifier + "'. Please check your username/email or sign up.");
        }

        String hashedInputPassword = hashPassword(password);
        User matchedUser = null;

        // Verify password against matched user(s)
        for (User candidate : userList) {
            if (candidate.getPassword() != null &&
               (candidate.getPassword().equals(hashedInputPassword) || candidate.getPassword().equals(password))) {
                matchedUser = candidate;
                break;
            }
        }

        if (matchedUser == null) {
            throw new IllegalArgumentException("Incorrect password for '" + identifier + "'. Please check your password or use 'Forgot Password'.");
        }

        return new AuthResponse(
            true,
            "Login successful",
            matchedUser.getId(),
            matchedUser.getUsername(),
            matchedUser.getEmail(),
            matchedUser.getFullName(),
            matchedUser.getRole()
        );
    }

    // 3. Reset Password
    public AuthResponse resetPassword(String identifier, String newPassword) {
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Username or email is required.");
        }
        if (newPassword == null || newPassword.trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }

        String trimmedId = identifier.trim();
        java.util.List<User> userList = userRepository.findByUsernameOrEmailIgnoreCase(trimmedId);
        if (userList == null || userList.isEmpty()) {
            userList = userRepository.findByIdentifierIgnoreCase(trimmedId);
        }

        if (userList == null || userList.isEmpty()) {
            throw new IllegalArgumentException("No account found with username or email: '" + trimmedId + "'.");
        }

        User user = userList.get(0);
        user.setPassword(hashPassword(newPassword.trim()));
        userRepository.save(user);

        return new AuthResponse(
            true,
            "Password has been reset successfully. You can now log in with your new password.",
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getRole()
        );
    }

    // Helper: SHA-256 cryptographic password hashing
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
