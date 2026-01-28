package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "SRSDocuments")
public class Srsdocuments {

    @Column(name = "DocID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "VersionNumber", nullable = true)
    private String versionnumber;

    @Column(name = "ExportedBy", nullable = true)
    private Integer exportedby;

    @Column(name = "ExportedDate", nullable = true)
    private LocalDateTime exporteddate;

    @Column(name = "FileLink", nullable = true)
    private String filelink;

    @Column(name = "SnapshotSummary", nullable = true)
    private String snapshotsummary;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    @ManyToOne
    @JoinColumn(name = "ExportedBy", referencedColumnName = "UserID")
    private Users users;

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getVersionnumber() {
        return versionnumber;
    }

    public void setVersionnumber(String versionnumber) {
        this.versionnumber = versionnumber;
    }

    public Integer getExportedby() {
        return exportedby;
    }

    public void setExportedby(Integer exportedby) {
        this.exportedby = exportedby;
    }

    public LocalDateTime getExporteddate() {
        return exporteddate;
    }

    public void setExporteddate(LocalDateTime exporteddate) {
        this.exporteddate = exporteddate;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public String getSnapshotsummary() {
        return snapshotsummary;
    }

    public void setSnapshotsummary(String snapshotsummary) {
        this.snapshotsummary = snapshotsummary;
    }

}
