package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Users")
public class Users {

    @Column(name = "UserID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    @Column(name = "FullName", nullable = false)
    private String fullname;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "PasswordHash", nullable = true)
    private String passwordhash;

    @Column(name = "Role", nullable = true)
    private String role;

    @Column(name = "JiraAccountId", nullable = true)
    private String jiraaccountid;

    @Column(name = "GitHubUsername", nullable = true)
    private String githubusername;

    @Column(name = "AvatarUrl", nullable = true)
    private String avatarurl;

    @Column(name = "IsActive", nullable = true)
    private Boolean isactive;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJiraaccountid() {
        return jiraaccountid;
    }

    public void setJiraaccountid(String jiraaccountid) {
        this.jiraaccountid = jiraaccountid;
    }

    public String getGithubusername() {
        return githubusername;
    }

    public void setGithubusername(String githubusername) {
        this.githubusername = githubusername;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

}
