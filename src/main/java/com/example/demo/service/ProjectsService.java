package com.example.demo.service;

import com.example.demo.entity.Projects;
import com.example.demo.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectsService {
    
    @Autowired
    private ProjectsRepository projectsRepository;
    
    // Get all projects
    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }
    
    // Get project by ID
    public Optional<Projects> getProjectById(Integer id) {
        return projectsRepository.findById(id);
    }
    
    // Get projects by course ID
    public List<Projects> getProjectsByCourseId(Integer courseId) {
        return projectsRepository.findByCourseid(courseId);
    }
    
    // Get projects by status
    public List<Projects> getProjectsByStatus(String status) {
        return projectsRepository.findByProjectstatus(status);
    }
    
    // Search projects by name
    public List<Projects> searchProjectsByName(String name) {
        return projectsRepository.searchByName(name);
    }
    
    // Create new project
    public Projects createProject(Projects project) {
        // Set default values
        if (project.getCreatedat() == null) {
            project.setCreatedat(LocalDateTime.now());
        }
        
        if (project.getProjectstatus() == null || project.getProjectstatus().trim().isEmpty()) {
            project.setProjectstatus("ACTIVE");
        }
        
        return projectsRepository.save(project);
    }
    
    // Update project
    public Projects updateProject(Integer id, Projects projectDetails) {
        Projects project = projectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        
        // Update fields
        if (projectDetails.getCourseid() != null) {
            project.setCourseid(projectDetails.getCourseid());
        }
        if (projectDetails.getGroupname() != null) {
            project.setGroupname(projectDetails.getGroupname());
        }
        if (projectDetails.getProjectname() != null) {
            project.setProjectname(projectDetails.getProjectname());
        }
        if (projectDetails.getDescription() != null) {
            project.setDescription(projectDetails.getDescription());
        }
        if (projectDetails.getProjectstatus() != null) {
            project.setProjectstatus(projectDetails.getProjectstatus());
        }
        
        return projectsRepository.save(project);
    }
    
    // Delete project
    public void deleteProject(Integer id) {
        Projects project = projectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectsRepository.delete(project);
    }
}
