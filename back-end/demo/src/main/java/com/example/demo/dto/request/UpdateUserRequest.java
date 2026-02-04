package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
    
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String role;
    private String jiraAccountId;
    private String gitHubUsername;
    private String avatarUrl;
    private Boolean isActive;
    
    public UpdateUserRequest() {}
    
    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getJiraAccountId() {
        return jiraAccountId;
    }
    
    public void setJiraAccountId(String jiraAccountId) {
        this.jiraAccountId = jiraAccountId;
    }
    
    public String getGitHubUsername() {
        return gitHubUsername;
    }
    
    public void setGitHubUsername(String gitHubUsername) {
        this.gitHubUsername = gitHubUsername;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
