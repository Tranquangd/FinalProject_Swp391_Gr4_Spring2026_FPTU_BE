package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ProjectRepositories")
public class ProjectRepositories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RepoID", nullable = false)
    private Integer repoId;
    
    @Column(name = "ProjectID")
    private Integer projectId;
    
    @Column(name = "RepoName", length = 100)
    private String repoName;
    
    @Column(name = "RepoOwner", length = 100)
    private String repoOwner;
    
    @Column(name = "RepoUrl", length = 500)
    private String repoUrl;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    // Constructors
    public ProjectRepositories() {}
    
    public ProjectRepositories(Integer projectId, String repoName, String repoOwner, String repoUrl) {
        this.projectId = projectId;
        this.repoName = repoName;
        this.repoOwner = repoOwner;
        this.repoUrl = repoUrl;
    }
    
    // Getters and Setters
    public Integer getRepoId() {
        return repoId;
    }
    
    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getRepoName() {
        return repoName;
    }
    
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
    
    public String getRepoOwner() {
        return repoOwner;
    }
    
    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }
    
    public String getRepoUrl() {
        return repoUrl;
    }
    
    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }
    
    public Projects getProject() {
        return project;
    }
    
    public void setProject(Projects project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "ProjectRepositories{" +
                "repoId=" + repoId +
                ", projectId=" + projectId +
                ", repoName='" + repoName + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", repoUrl='" + repoUrl + '\'' +
                '}';
    }
}
