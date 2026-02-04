package com.example.demo.dto.response;

public class UserResponse {
    
    private Integer userId;
    private String fullName;
    private String email;
    private String role;
    private String jiraAccountId;
    private String gitHubUsername;
    private String avatarUrl;
    private Boolean isActive;
    
    public UserResponse() {}
    
    public UserResponse(Integer userId, String fullName, String email, String role, 
                       String jiraAccountId, String gitHubUsername, String avatarUrl, Boolean isActive) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.jiraAccountId = jiraAccountId;
        this.gitHubUsername = gitHubUsername;
        this.avatarUrl = avatarUrl;
        this.isActive = isActive;
    }
    
    // Getters and Setters
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
