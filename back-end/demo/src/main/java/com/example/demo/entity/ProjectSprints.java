package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProjectSprints")
public class ProjectSprints {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SprintID", nullable = false)
    private Integer sprintId;
    
    @Column(name = "ProjectID")
    private Integer projectId;
    
    @Column(name = "JiraSprintId")
    private Integer jiraSprintId;
    
    @Column(name = "SprintName", length = 100)
    private String sprintName;
    
    @Column(name = "StartDate")
    private LocalDateTime startDate;
    
    @Column(name = "EndDate")
    private LocalDateTime endDate;
    
    @Column(name = "SprintState", length = 20)
    private String sprintState;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    // Constructors
    public ProjectSprints() {}
    
    public ProjectSprints(Integer projectId, String sprintName, LocalDateTime startDate, LocalDateTime endDate) {
        this.projectId = projectId;
        this.sprintName = sprintName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprintState = "future";
    }
    
    // Getters and Setters
    public Integer getSprintId() {
        return sprintId;
    }
    
    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getJiraSprintId() {
        return jiraSprintId;
    }
    
    public void setJiraSprintId(Integer jiraSprintId) {
        this.jiraSprintId = jiraSprintId;
    }
    
    public String getSprintName() {
        return sprintName;
    }
    
    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public String getSprintState() {
        return sprintState;
    }
    
    public void setSprintState(String sprintState) {
        this.sprintState = sprintState;
    }
    
    public Projects getProject() {
        return project;
    }
    
    public void setProject(Projects project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "ProjectSprints{" +
                "sprintId=" + sprintId +
                ", projectId=" + projectId +
                ", sprintName='" + sprintName + '\'' +
                ", sprintState='" + sprintState + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
