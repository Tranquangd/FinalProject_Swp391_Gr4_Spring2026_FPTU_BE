package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SRSDocuments")
public class SrsDocuments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocID", nullable = false)
    private Integer docId;
    
    @Column(name = "ProjectID")
    private Integer projectId;
    
    @Column(name = "VersionNumber", length = 20)
    private String versionNumber;
    
    @Column(name = "ExportedBy")
    private Integer exportedBy;
    
    @Column(name = "ExportedDate")
    private LocalDateTime exportedDate;
    
    @Column(name = "FileLink", length = 500)
    private String fileLink;
    
    @Column(name = "SnapshotSummary", columnDefinition = "TEXT")
    private String snapshotSummary;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    @ManyToOne
    @JoinColumn(name = "ExportedBy", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users user;
    
    // Constructors
    public SrsDocuments() {}
    
    public SrsDocuments(Integer projectId, String versionNumber, Integer exportedBy) {
        this.projectId = projectId;
        this.versionNumber = versionNumber;
        this.exportedBy = exportedBy;
        this.exportedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getDocId() {
        return docId;
    }
    
    public void setDocId(Integer docId) {
        this.docId = docId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getVersionNumber() {
        return versionNumber;
    }
    
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
    
    public Integer getExportedBy() {
        return exportedBy;
    }
    
    public void setExportedBy(Integer exportedBy) {
        this.exportedBy = exportedBy;
    }
    
    public LocalDateTime getExportedDate() {
        return exportedDate;
    }
    
    public void setExportedDate(LocalDateTime exportedDate) {
        this.exportedDate = exportedDate;
    }
    
    public String getFileLink() {
        return fileLink;
    }
    
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
    
    public String getSnapshotSummary() {
        return snapshotSummary;
    }
    
    public void setSnapshotSummary(String snapshotSummary) {
        this.snapshotSummary = snapshotSummary;
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
        return "SrsDocuments{" +
                "docId=" + docId +
                ", projectId=" + projectId +
                ", versionNumber='" + versionNumber + '\'' +
                ", exportedBy=" + exportedBy +
                ", exportedDate=" + exportedDate +
                '}';
    }
}
