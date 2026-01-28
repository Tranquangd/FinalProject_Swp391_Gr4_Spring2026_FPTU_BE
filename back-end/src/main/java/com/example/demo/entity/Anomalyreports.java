package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "AnomalyReports")
public class Anomalyreports {

    @Column(name = "ReportID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "DetectedDate", nullable = true)
    private LocalDateTime detecteddate;

    @Column(name = "IssueType", nullable = true)
    private String issuetype;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "Severity", nullable = true)
    private String severity;

    @Column(name = "RelatedUserID", nullable = true)
    private Integer relateduserid;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    @ManyToOne
    @JoinColumn(name = "RelatedUserID", referencedColumnName = "UserID")
    private Users users;

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public LocalDateTime getDetecteddate() {
        return detecteddate;
    }

    public void setDetecteddate(LocalDateTime detecteddate) {
        this.detecteddate = detecteddate;
    }

    public String getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
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

    public Integer getRelateduserid() {
        return relateduserid;
    }

    public void setRelateduserid(Integer relateduserid) {
        this.relateduserid = relateduserid;
    }

}
