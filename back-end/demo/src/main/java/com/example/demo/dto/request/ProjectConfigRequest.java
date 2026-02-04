package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectConfigRequest {
    
    @NotNull(message = "Project ID is required")
    private Integer projectId;
    
    @NotBlank(message = "Jira URL is required")
    private String jiraUrl;
    
    @NotBlank(message = "Jira project key is required")
    private String jiraProjectKey;
    
    @NotBlank(message = "Jira email is required")
    private String jiraEmail;
    
    @NotBlank(message = "Jira API token is required")
    private String jiraApiToken;
    
    private String gitHubToken;
    
    public ProjectConfigRequest() {}
    
    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getJiraUrl() {
        return jiraUrl;
    }
    
    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }
    
    public String getJiraProjectKey() {
        return jiraProjectKey;
    }
    
    public void setJiraProjectKey(String jiraProjectKey) {
        this.jiraProjectKey = jiraProjectKey;
    }
    
    public String getJiraEmail() {
        return jiraEmail;
    }
    
    public void setJiraEmail(String jiraEmail) {
        this.jiraEmail = jiraEmail;
    }
    
    public String getJiraApiToken() {
        return jiraApiToken;
    }
    
    public void setJiraApiToken(String jiraApiToken) {
        this.jiraApiToken = jiraApiToken;
    }
    
    public String getGitHubToken() {
        return gitHubToken;
    }
    
    public void setGitHubToken(String gitHubToken) {
        this.gitHubToken = gitHubToken;
    }
}
