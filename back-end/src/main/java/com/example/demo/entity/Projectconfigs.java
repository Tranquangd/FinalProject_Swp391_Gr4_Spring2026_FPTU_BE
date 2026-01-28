package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ProjectConfigs")
public class Projectconfigs {

    @Column(name = "ConfigID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer configid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "JiraUrl", nullable = true)
    private String jiraurl;

    @Column(name = "JiraProjectKey", nullable = true)
    private String jiraprojectkey;

    @Column(name = "JiraEmail", nullable = true)
    private String jiraemail;

    @Column(name = "JiraApiToken", nullable = true)
    private String jiraapitoken;

    @Column(name = "GitHubToken", nullable = true)
    private String githubtoken;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    public Integer getConfigid() {
        return configid;
    }

    public void setConfigid(Integer configid) {
        this.configid = configid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getJiraurl() {
        return jiraurl;
    }

    public void setJiraurl(String jiraurl) {
        this.jiraurl = jiraurl;
    }

    public String getJiraprojectkey() {
        return jiraprojectkey;
    }

    public void setJiraprojectkey(String jiraprojectkey) {
        this.jiraprojectkey = jiraprojectkey;
    }

    public String getJiraemail() {
        return jiraemail;
    }

    public void setJiraemail(String jiraemail) {
        this.jiraemail = jiraemail;
    }

    public String getJiraapitoken() {
        return jiraapitoken;
    }

    public void setJiraapitoken(String jiraapitoken) {
        this.jiraapitoken = jiraapitoken;
    }

    public String getGithubtoken() {
        return githubtoken;
    }

    public void setGithubtoken(String githubtoken) {
        this.githubtoken = githubtoken;
    }

}
