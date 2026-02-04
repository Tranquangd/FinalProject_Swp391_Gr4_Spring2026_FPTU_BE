package com.example.demo.dto;

public class AuthResponse {
    private String token;
    private String email;
    private Integer userId;
    private String fullName;
    private String role;
    private String message;
    
    public AuthResponse() {
    }
    
    public AuthResponse(String token, String email, Integer userId, String fullName, String role) {
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
    }
    
    public AuthResponse(String message) {
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
