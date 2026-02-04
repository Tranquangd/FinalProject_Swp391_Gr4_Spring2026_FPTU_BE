package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "GitHubCommits")
public class GithubCommits {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommitID", nullable = false)
    private Integer commitId;
    
    @Column(name = "RepoID")
    private Integer repoId;
    
    @Column(name = "SHA", length = 100)
    private String sha;
    
    @Column(name = "CommitterName", length = 100)
    private String committerName;
    
    @Column(name = "CommitterEmail", length = 100)
    private String committerEmail;
    
    @Column(name = "CommitMessage", columnDefinition = "TEXT")
    private String commitMessage;
    
    @Column(name = "Additions")
    private Integer additions;
    
    @Column(name = "Deletions")
    private Integer deletions;
    
    @Column(name = "CommitDate")
    private LocalDateTime commitDate;
    
    @Column(name = "CommitUrl", length = 500)
    private String commitUrl;
    
    @ManyToOne
    @JoinColumn(name = "RepoID", referencedColumnName = "RepoID", insertable = false, updatable = false)
    private ProjectRepositories repository;
    
    // Constructors
    public GithubCommits() {}
    
    public GithubCommits(Integer repoId, String sha, String committerName, String committerEmail, String commitMessage) {
        this.repoId = repoId;
        this.sha = sha;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
        this.commitMessage = commitMessage;
        this.commitDate = LocalDateTime.now();
    }
    
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
    
    public ProjectRepositories getRepository() {
        return repository;
    }
    
    public void setRepository(ProjectRepositories repository) {
        this.repository = repository;
    }
    
    @Override
    public String toString() {
        return "GithubCommits{" +
                "commitId=" + commitId +
                ", repoId=" + repoId +
                ", sha='" + sha + '\'' +
                ", committerName='" + committerName + '\'' +
                ", committerEmail='" + committerEmail + '\'' +
                ", commitDate=" + commitDate +
                '}';
    }
}
