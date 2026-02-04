package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;

public class AddProjectMemberRequest {
    
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    @NotNull(message = "Role is required")
    private String role; // LEADER, MEMBER
    
    public AddProjectMemberRequest() {}
    
    public AddProjectMemberRequest(Integer userId, String role) {
        this.userId = userId;
        this.role = role;
    }
    
    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
