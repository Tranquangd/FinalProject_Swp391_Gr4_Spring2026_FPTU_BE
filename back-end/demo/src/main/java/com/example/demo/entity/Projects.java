package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Projects")
public class Projects {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID", nullable = false)
    private Integer projectId;
    
    @Column(name = "CourseID")
    private Integer courseId;
    
    @Column(name = "GroupName", length = 50)
    private String groupName;
    
    @Column(name = "ProjectName", length = 200)
    private String projectName;
    
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "ProjectStatus", length = 20)
    private String projectStatus;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", insertable = false, updatable = false)
    private Courses course;
    
    // Constructors
    public Projects() {}
    
    public Projects(Integer courseId, String groupName, String projectName, String description) {
        this.courseId = courseId;
        this.groupName = groupName;
        this.projectName = projectName;
        this.description = description;
        this.projectStatus = "IN_PROGRESS";
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getProjectStatus() {
        return projectStatus;
    }
    
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Courses getCourse() {
        return course;
    }
    
    public void setCourse(Courses course) {
        this.course = course;
    }
    
    @Override
    public String toString() {
        return "Projects{" +
                "projectId=" + projectId +
                ", courseId=" + courseId +
                ", groupName='" + groupName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
