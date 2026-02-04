package com.example.demo.dto.response;

public class MemberStatisticsResponse {
    
    private Integer userId;
    private String fullName;
    private String email;
    private String jiraAccountId;
    private String gitHubUsername;
    
    // Task statistics
    private Long totalTasksAssigned;
    private Long tasksCompleted;
    private Long tasksInProgress;
    private Integer totalStoryPoints;
    private Integer completedStoryPoints;
    
    // Commit statistics
    private Long totalCommits;
    private Integer totalAdditions;
    private Integer totalDeletions;
    private Integer netChanges; // additions - deletions
    
    // Performance metrics
    private Double taskCompletionRate; // (completed / total) * 100
    private Double averageCommitsPerDay;
    
    public MemberStatisticsResponse() {}
    
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
    
    public Long getTotalTasksAssigned() {
        return totalTasksAssigned;
    }
    
    public void setTotalTasksAssigned(Long totalTasksAssigned) {
        this.totalTasksAssigned = totalTasksAssigned;
    }
    
    public Long getTasksCompleted() {
        return tasksCompleted;
    }
    
    public void setTasksCompleted(Long tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }
    
    public Long getTasksInProgress() {
        return tasksInProgress;
    }
    
    public void setTasksInProgress(Long tasksInProgress) {
        this.tasksInProgress = tasksInProgress;
    }
    
    public Integer getTotalStoryPoints() {
        return totalStoryPoints;
    }
    
    public void setTotalStoryPoints(Integer totalStoryPoints) {
        this.totalStoryPoints = totalStoryPoints;
    }
    
    public Integer getCompletedStoryPoints() {
        return completedStoryPoints;
    }
    
    public void setCompletedStoryPoints(Integer completedStoryPoints) {
        this.completedStoryPoints = completedStoryPoints;
    }
    
    public Long getTotalCommits() {
        return totalCommits;
    }
    
    public void setTotalCommits(Long totalCommits) {
        this.totalCommits = totalCommits;
    }
    
    public Integer getTotalAdditions() {
        return totalAdditions;
    }
    
    public void setTotalAdditions(Integer totalAdditions) {
        this.totalAdditions = totalAdditions;
    }
    
    public Integer getTotalDeletions() {
        return totalDeletions;
    }
    
    public void setTotalDeletions(Integer totalDeletions) {
        this.totalDeletions = totalDeletions;
    }
    
    public Integer getNetChanges() {
        return netChanges;
    }
    
    public void setNetChanges(Integer netChanges) {
        this.netChanges = netChanges;
    }
    
    public Double getTaskCompletionRate() {
        return taskCompletionRate;
    }
    
    public void setTaskCompletionRate(Double taskCompletionRate) {
        this.taskCompletionRate = taskCompletionRate;
    }
    
    public Double getAverageCommitsPerDay() {
        return averageCommitsPerDay;
    }
    
    public void setAverageCommitsPerDay(Double averageCommitsPerDay) {
        this.averageCommitsPerDay = averageCommitsPerDay;
    }
}
