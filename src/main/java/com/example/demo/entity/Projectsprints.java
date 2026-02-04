package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ProjectSprints")
public class Projectsprints {

    @Column(name = "SprintID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sprintid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "JiraSprintId", nullable = true)
    private Integer jirasprintid;

    @Column(name = "SprintName", nullable = true)
    private String sprintname;

    @Column(name = "StartDate", nullable = true)
    private LocalDateTime startdate;

    @Column(name = "EndDate", nullable = true)
    private LocalDateTime enddate;

    @Column(name = "SprintState", nullable = true)
    private String sprintstate;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    public Integer getSprintid() {
        return sprintid;
    }

    public void setSprintid(Integer sprintid) {
        this.sprintid = sprintid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getJirasprintid() {
        return jirasprintid;
    }

    public void setJirasprintid(Integer jirasprintid) {
        this.jirasprintid = jirasprintid;
    }

    public String getSprintname() {
        return sprintname;
    }

    public void setSprintname(String sprintname) {
        this.sprintname = sprintname;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public String getSprintstate() {
        return sprintstate;
    }

    public void setSprintstate(String sprintstate) {
        this.sprintstate = sprintstate;
    }

}
