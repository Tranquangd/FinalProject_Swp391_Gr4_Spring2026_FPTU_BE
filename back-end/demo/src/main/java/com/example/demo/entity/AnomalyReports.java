package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AnomalyReports")
public class AnomalyReports {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID", nullable = false)
    private Integer reportId;
    
    @Column(name = "ProjectID")
    private Integer projectId;
    
    @Column(name = "DetectedDate")
    private LocalDateTime detectedDate;
    
    @Column(name = "IssueType", length = 50)
    private String issueType;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "Severity", length = 20)
    private String severity;
    
    @Column(name = "RelatedUserID")
    private Integer relatedUserId;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    @ManyToOne
    @JoinColumn(name = "RelatedUserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users user;
    
    // Constructors
    public AnomalyReports() {}
    
    public AnomalyReports(Integer projectId, String issueType, String description, String severity, Integer relatedUserId) {
        this.projectId = projectId;
        this.detectedDate = LocalDateTime.now();
        this.issueType = issueType;
        this.description = description;
        this.severity = severity;
        this.relatedUserId = relatedUserId;
    }
    
    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }
    
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public LocalDateTime getDetectedDate() {
        return detectedDate;
    }
    
    public void setDetectedDate(LocalDateTime detectedDate) {
        this.detectedDate = detectedDate;
    }
    
    public String getIssueType() {
        return issueType;
    }
    
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public Integer getRelatedUserId() {
        return relatedUserId;
    }
    
    public void setRelatedUserId(Integer relatedUserId) {
        this.relatedUserId = relatedUserId;
    }
    
    public Projects getProject() {
        return project;
    }
    
    public void setProject(Projects project) {
        this.project = project;
    }
    
    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "AnomalyReports{" +
                "reportId=" + reportId +
                ", projectId=" + projectId +
                ", detectedDate=" + detectedDate +
                ", issueType='" + issueType + '\'' +
                ", severity='" + severity + '\'' +
                ", relatedUserId=" + relatedUserId +
                '}';
    }
}
