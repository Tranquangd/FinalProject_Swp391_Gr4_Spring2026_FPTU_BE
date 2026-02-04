package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "GitHubCommits")
public class Githubcommits {

    @Column(name = "CommitID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commitid;

    @Column(name = "RepoID", nullable = true)
    private Integer repoid;

    @Column(name = "SHA", nullable = true)
    private String sha;

    @Column(name = "CommitterName", nullable = true)
    private String committername;

    @Column(name = "CommitterEmail", nullable = true)
    private String committeremail;

    @Column(name = "CommitMessage", nullable = true)
    private String commitmessage;

    @Column(name = "Additions", nullable = true)
    private Integer additions;

    @Column(name = "Deletions", nullable = true)
    private Integer deletions;

    @Column(name = "CommitDate", nullable = true)
    private LocalDateTime commitdate;

    @Column(name = "CommitUrl", nullable = true)
    private String commiturl;

    @ManyToOne
    @JoinColumn(name = "RepoID", referencedColumnName = "RepoID")
    private Projectrepositories projectrepositories;

    public Integer getCommitid() {
        return commitid;
    }

    public void setCommitid(Integer commitid) {
        this.commitid = commitid;
    }

    public Integer getRepoid() {
        return repoid;
    }

    public void setRepoid(Integer repoid) {
        this.repoid = repoid;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getCommittername() {
        return committername;
    }

    public void setCommittername(String committername) {
        this.committername = committername;
    }

    public String getCommitteremail() {
        return committeremail;
    }

    public void setCommitteremail(String committeremail) {
        this.committeremail = committeremail;
    }

    public String getCommitmessage() {
        return commitmessage;
    }

    public void setCommitmessage(String commitmessage) {
        this.commitmessage = commitmessage;
    }

    public Integer getAdditions() {
        return additions;
    }

    public void setAdditions(Integer additions) {
        this.additions = additions;
    }

    public Integer getDeletions() {
        return deletions;
    }

    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    public LocalDateTime getCommitdate() {
        return commitdate;
    }

    public void setCommitdate(LocalDateTime commitdate) {
        this.commitdate = commitdate;
    }

    public String getCommiturl() {
        return commiturl;
    }

    public void setCommiturl(String commiturl) {
        this.commiturl = commiturl;
    }

}
