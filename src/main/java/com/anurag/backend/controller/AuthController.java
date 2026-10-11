package com.anurag.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anurag.backend.dto.AuthResponse;
import com.anurag.backend.dto.LoginRequest;
import com.anurag.backend.dto.RegisterRequest;
import com.anurag.backend.service.UserService;

/**
 * Controller Class: AuthController
 * Exposes REST API endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // POST /api/auth/register
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = userService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new AuthResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new AuthResponse(false, "Registration error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = userService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new AuthResponse(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new AuthResponse(false, "Login error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/auth/reset-password
    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(@RequestBody java.util.Map<String, String> body) {
        try {
            String identifier = body.get("usernameOrEmail");
            if (identifier == null || identifier.trim().isEmpty()) {
                identifier = body.get("username");
            }
            if (identifier == null || identifier.trim().isEmpty()) {
                identifier = body.get("email");
            }
            String newPassword = body.get("newPassword");
            if (newPassword == null || newPassword.trim().isEmpty()) {
                newPassword = body.get("password");
            }

            AuthResponse response = userService.resetPassword(identifier, newPassword);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new AuthResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(new AuthResponse(false, "Password reset error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
