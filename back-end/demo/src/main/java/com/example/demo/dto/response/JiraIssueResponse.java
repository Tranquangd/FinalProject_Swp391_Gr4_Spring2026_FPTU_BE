package com.example.demo.dto.response;

import java.time.LocalDateTime;

public class JiraIssueResponse {
    
    private Integer issueId;
    private Integer projectId;
    private Integer sprintId;
    private String sprintName;
    private String jiraKey;
    private String issueType;
    private String summary;
    private String description;
    private String acceptanceCriteria;
    private Integer storyPoint;
    private String priority;
    private String status;
    private String assigneeJiraId;
    private String assigneeName;
    private LocalDateTime lastSyncDate;
    
    public JiraIssueResponse() {}
    
    // Getters and Setters
    public Integer getIssueId() {
        return issueId;
    }
    
    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getSprintId() {
        return sprintId;
    }
    
    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }
    
    public String getSprintName() {
        return sprintName;
    }
    
    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
    
    public String getJiraKey() {
        return jiraKey;
    }
    
    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }
    
    public String getIssueType() {
        return issueType;
    }
    
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }
    
    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }
    
    public Integer getStoryPoint() {
        return storyPoint;
    }
    
    public void setStoryPoint(Integer storyPoint) {
        this.storyPoint = storyPoint;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAssigneeJiraId() {
        return assigneeJiraId;
    }
    
    public void setAssigneeJiraId(String assigneeJiraId) {
        this.assigneeJiraId = assigneeJiraId;
    }
    
    public String getAssigneeName() {
        return assigneeName;
    }
    
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
    
    public LocalDateTime getLastSyncDate() {
        return lastSyncDate;
    }
    
    public void setLastSyncDate(LocalDateTime lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
