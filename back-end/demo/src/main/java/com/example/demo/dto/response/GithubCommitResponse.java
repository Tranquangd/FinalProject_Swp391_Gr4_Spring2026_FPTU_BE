package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class GithubCommitResponse {
    
    private Integer commitId;
    private Integer repoId;
    private String repoName;
    private String sha;
    private String committerName;
    private String committerEmail;
    private String commitMessage;
    private Integer additions;
    private Integer deletions;
    private LocalDateTime commitDate;
    private String commitUrl;
    
    public GithubCommitResponse() {}
    
    // Getters and Setters
    public Integer getCommitId() {
        return commitId;
    }
    
    public void setCommitId(Integer commitId) {
        this.commitId = commitId;
    }
    
    public Integer getRepoId() {
        return repoId;
    }
    
    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }
    
    public String getRepoName() {
        return repoName;
    }
    
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
    
    public String getSha() {
        return sha;
    }
    
    public void setSha(String sha) {
        this.sha = sha;
    }
    
    public String getCommitterName() {
        return committerName;
    }
    
    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }
    
    public String getCommitterEmail() {
        return committerEmail;
    }
    
    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }
    
    public String getCommitMessage() {
        return commitMessage;
    }
    
    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }
    
    public Integer getAdditions() {
        return additions;
    }
    
    public void setAdditions(Integer additions) {
        this.additions = additions;
    }
    
    public Integer getDeletions() {
        return deletions;
    }
    
    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }
    
    public LocalDateTime getCommitDate() {
        return commitDate;
    }
    
    public void setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
    }
    
    public String getCommitUrl() {
        return commitUrl;
    }
    
    public void setCommitUrl(String commitUrl) {
        this.commitUrl = commitUrl;
    }
}
