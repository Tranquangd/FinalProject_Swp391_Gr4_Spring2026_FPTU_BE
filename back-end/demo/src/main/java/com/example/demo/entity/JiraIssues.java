package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "JiraIssues")
public class JiraIssues {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IssueID", nullable = false)
    private Integer issueId;
    
    @Column(name = "ProjectID")
    private Integer projectId;
    
    @Column(name = "SprintID")
    private Integer sprintId;
    
    @Column(name = "JiraKey", length = 50)
    private String jiraKey;
    
    @Column(name = "IssueType", length = 50)
    private String issueType;
    
    @Column(name = "Summary", length = 500)
    private String summary;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "AcceptanceCriteria", columnDefinition = "TEXT")
    private String acceptanceCriteria;
    
    @Column(name = "StoryPoint")
    private Integer storyPoint;
    
    @Column(name = "Priority", length = 20)
    private String priority;
    
    @Column(name = "Status", length = 50)
    private String status;
    
    @Column(name = "AssigneeJiraID", length = 100)
    private String assigneeJiraId;
    
    @Column(name = "LastSyncDate")
    private LocalDateTime lastSyncDate;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    @ManyToOne
    @JoinColumn(name = "SprintID", referencedColumnName = "SprintID", insertable = false, updatable = false)
    private ProjectSprints sprint;
    
    // Constructors
    public JiraIssues() {}
    
    public JiraIssues(Integer projectId, String jiraKey, String issueType, String summary) {
        this.projectId = projectId;
        this.jiraKey = jiraKey;
        this.issueType = issueType;
        this.summary = summary;
        this.lastSyncDate = LocalDateTime.now();
    }
    
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
    
    public LocalDateTime getLastSyncDate() {
        return lastSyncDate;
    }
    
    public void setLastSyncDate(LocalDateTime lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
    
    public Projects getProject() {
        return project;
    }
    
    public void setProject(Projects project) {
        this.project = project;
    }
    
    public ProjectSprints getSprint() {
        return sprint;
    }
    
    public void setSprint(ProjectSprints sprint) {
        this.sprint = sprint;
    }
    
    @Override
    public String toString() {
        return "JiraIssues{" +
                "issueId=" + issueId +
                ", projectId=" + projectId +
                ", jiraKey='" + jiraKey + '\'' +
                ", issueType='" + issueType + '\'' +
                ", summary='" + summary + '\'' +
                ", status='" + status + '\'' +
                ", assigneeJiraId='" + assigneeJiraId + '\'' +
                '}';
    }
}
