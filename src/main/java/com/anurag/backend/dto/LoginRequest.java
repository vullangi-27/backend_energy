package com.anurag.backend.dto;

/**
 * Data Transfer Object for user login requests.
 * Accepts either username or email along with the password.
 */
public class LoginRequest {

    private String usernameOrEmail;
    private String username;
    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() {
        if (usernameOrEmail != null && !usernameOrEmail.trim().isEmpty()) {
            return usernameOrEmail.trim();
        }
        if (username != null && !username.trim().isEmpty()) {
            return username.trim();
        }
        if (email != null && !email.trim().isEmpty()) {
            return email.trim();
        }
        return null;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        if (this.usernameOrEmail == null || this.usernameOrEmail.trim().isEmpty()) {
            this.usernameOrEmail = username;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (this.usernameOrEmail == null || this.usernameOrEmail.trim().isEmpty()) {
            this.usernameOrEmail = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
