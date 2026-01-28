package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ProjectRepositories")
public class Projectrepositories {

    @Column(name = "RepoID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repoid;

    @Column(name = "ProjectID", nullable = true)
    private Integer projectid;

    @Column(name = "RepoName", nullable = true)
    private String reponame;

    @Column(name = "RepoOwner", nullable = true)
    private String repoowner;

    @Column(name = "RepoUrl", nullable = true)
    private String repourl;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    public Integer getRepoid() {
        return repoid;
    }

    public void setRepoid(Integer repoid) {
        this.repoid = repoid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getReponame() {
        return reponame;
    }

    public void setReponame(String reponame) {
        this.reponame = reponame;
    }

    public String getRepoowner() {
        return repoowner;
    }

    public void setRepoowner(String repoowner) {
        this.repoowner = repoowner;
    }

    public String getRepourl() {
        return repourl;
    }

    public void setRepourl(String repourl) {
        this.repourl = repourl;
    }

}
