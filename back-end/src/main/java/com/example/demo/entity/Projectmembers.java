package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ProjectMembers")
public class Projectmembers {

    @Column(name = "ProjectID", nullable = false)
    @Id
    private Integer projectid;

    @Column(name = "UserID", nullable = false)
    @Id
    private Integer userid;

    @Column(name = "Role", nullable = true)
    private String role;

    @Column(name = "IsActive", nullable = true)
    private Boolean isactive;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")
    private Projects projects;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private Users users;

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

}
