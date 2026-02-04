package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "JiraIssues")
public class Jiraissues {

    @Column(name = "IssueID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "SprintID", nullable = true)
    private Integer sprintid;

    @Column(name = "JiraKey", nullable = true)
    private String jirakey;

    @Column(name = "IssueType", nullable = true)
    private String issuetype;

    @Column(name = "Summary", nullable = true)
    private String summary;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "AcceptanceCriteria", nullable = true)
    private String acceptancecriteria;

    @Column(name = "StoryPoint", nullable = true)
    private Integer storypoint;

    @Column(name = "Priority", nullable = true)
    private String priority;

    @Column(name = "Status", nullable = true)
    private String status;

    @Column(name = "AssigneeJiraID", nullable = true)
    private String assigneejiraid;

    @Column(name = "LastSyncDate", nullable = true)
    private LocalDateTime lastsyncdate;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    @ManyToOne
    @JoinColumn(name = "SprintID", referencedColumnName = "SprintID")
    private Projectsprints projectsprints;

    public Integer getIssueid() {
        return issueid;
    }

    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getSprintid() {
        return sprintid;
    }

    public void setSprintid(Integer sprintid) {
        this.sprintid = sprintid;
    }

    public String getJirakey() {
        return jirakey;
    }

    public void setJirakey(String jirakey) {
        this.jirakey = jirakey;
    }

    public String getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
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

    public String getAcceptancecriteria() {
        return acceptancecriteria;
    }

    public void setAcceptancecriteria(String acceptancecriteria) {
        this.acceptancecriteria = acceptancecriteria;
    }

    public Integer getStorypoint() {
        return storypoint;
    }

    public void setStorypoint(Integer storypoint) {
        this.storypoint = storypoint;
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

    public String getAssigneejiraid() {
        return assigneejiraid;
    }

    public void setAssigneejiraid(String assigneejiraid) {
        this.assigneejiraid = assigneejiraid;
    }

    public LocalDateTime getLastsyncdate() {
        return lastsyncdate;
    }

    public void setLastsyncdate(LocalDateTime lastsyncdate) {
        this.lastsyncdate = lastsyncdate;
    }

}
