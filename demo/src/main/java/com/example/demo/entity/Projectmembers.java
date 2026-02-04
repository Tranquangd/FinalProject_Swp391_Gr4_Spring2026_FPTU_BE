package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProjectMembers")
@IdClass(Projectmembers.ProjectMemberId.class)
public class Projectmembers {

    @Id
    @Column(name = "ProjectID", nullable = false)
    private Integer projectid;

    @Id
    @Column(name = "UserID", nullable = false)
    private Integer userid;

    @Column(name = "Role", nullable = true)
    private String role;

    @Column(name = "IsActive", nullable = true)
    private Boolean isactive;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects projects;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users users;

    // Composite Key Class
    public static class ProjectMemberId implements Serializable {
        private Integer projectid;
        private Integer userid;

        public ProjectMemberId() {
        }

        public ProjectMemberId(Integer projectid, Integer userid) {
            this.projectid = projectid;
            this.userid = userid;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProjectMemberId that = (ProjectMemberId) o;
            if (projectid != null ? !projectid.equals(that.projectid) : that.projectid != null) return false;
            return userid != null ? userid.equals(that.userid) : that.userid == null;
        }

        @Override
        public int hashCode() {
            int result = projectid != null ? projectid.hashCode() : 0;
            result = 31 * result + (userid != null ? userid.hashCode() : 0);
            return result;
        }
    }

    // Constructors
    public Projectmembers() {
    }

    public Projectmembers(Integer projectid, Integer userid) {
        this.projectid = projectid;
        this.userid = userid;
    }

    // Getters and Setters
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

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
