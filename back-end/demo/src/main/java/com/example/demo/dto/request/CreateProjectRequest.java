package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProjectRequest {
    
    @NotNull(message = "Course ID is required")
    private Integer courseId;
    
    @NotBlank(message = "Group name is required")
    @Size(max = 50, message = "Group name cannot exceed 50 characters")
    private String groupName;
    
    @NotBlank(message = "Project name is required")
    @Size(max = 200, message = "Project name cannot exceed 200 characters")
    private String projectName;
    
    private String description;
    private String projectStatus; // IN_PROGRESS, COMPLETED
    
    public CreateProjectRequest() {}
    
    // Getters and Setters
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
}
