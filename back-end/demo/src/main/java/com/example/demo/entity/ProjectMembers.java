package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProjectMembers")
@IdClass(ProjectMembersId.class)
public class ProjectMembers implements Serializable {
    
    @Id
    @Column(name = "ProjectID", nullable = false)
    private Integer projectId;
    
    @Id
    @Column(name = "UserID", nullable = false)
    private Integer userId;
    
    @Column(name = "Role", length = 20)
    private String role; // LEADER, MEMBER
    
    @Column(name = "IsActive")
    private Boolean isActive;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", insertable = false, updatable = false)
    private Projects project;
    
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users user;
    
    // Constructors
    public ProjectMembers() {}
    
    public ProjectMembers(Integer projectId, Integer userId, String role) {
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
        this.isActive = true;
    }
    
    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        return "ProjectMembers{" +
                "projectId=" + projectId +
                ", userId=" + userId +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

// Composite key class
class ProjectMembersId implements Serializable {
    private Integer projectId;
    private Integer userId;
    
    public ProjectMembersId() {}
    
    public ProjectMembersId(Integer projectId, Integer userId) {
        this.projectId = projectId;
        this.userId = userId;
    }
    
    // Getters, Setters, equals, and hashCode
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMembersId that = (ProjectMembersId) o;
        return projectId.equals(that.projectId) && userId.equals(that.userId);
    }
    
    @Override
    public int hashCode() {
        return 31 * projectId.hashCode() + userId.hashCode();
    }
}
