package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ProjectConfigs")
public class ProjectConfigs {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfigID", nullable = false)
    private Integer configId;
    
    @Column(name = "ProjectID", unique = true)
    private Integer projectId;
    
    @Column(name = "JiraUrl", length = 500)
    private String jiraUrl;
    
    @Column(name = "JiraProjectKey", length = 50)
    private String jiraProjectKey;
    
    @Column(name = "JiraEmail", length = 100)
    private String jiraEmail;
    
    @Column(name = "JiraApiToken", length = 500)
    private String jiraApiToken;
    
    @Column(name = "GitHubToken", length = 500)
    private String gitHubToken;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    // Constructors
    public ProjectConfigs() {}
    
    public ProjectConfigs(Integer projectId, String jiraUrl, String jiraProjectKey) {
        this.projectId = projectId;
        this.jiraUrl = jiraUrl;
        this.jiraProjectKey = jiraProjectKey;
    }
    
    // Getters and Setters
    public Integer getConfigId() {
        return configId;
    }
    
    public void setConfigId(Integer configId) {
        this.configId = configId;
    }
    
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
    
    public Projects getProject() {
        return project;
    }
    
    public void setProject(Projects project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "ProjectConfigs{" +
                "configId=" + configId +
                ", projectId=" + projectId +
                ", jiraUrl='" + jiraUrl + '\'' +
                ", jiraProjectKey='" + jiraProjectKey + '\'' +
                '}';
    }
}
