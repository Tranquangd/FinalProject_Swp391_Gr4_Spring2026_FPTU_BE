package com.example.demo.service;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Courses;
import com.example.demo.entity.Projects;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProjectMemberRepository;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    
    // Get all projects
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get project by ID
    public ProjectResponse getProjectById(Integer id) {
        Projects project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return convertToResponse(project);
    }
    
    // Get projects by course
    public List<ProjectResponse> getProjectsByCourse(Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
        return projectRepository.findByCourseId(courseId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get projects by lecturer
    public List<ProjectResponse> getProjectsByLecturer(Integer lecturerId) {
        return projectRepository.findByLecturerId(lecturerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get projects by status
    public List<ProjectResponse> getProjectsByStatus(String status) {
        return projectRepository.findByProjectStatus(status).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Search projects by name
    public List<ProjectResponse> searchProjectsByName(String name) {
        return projectRepository.searchByProjectName(name).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Create new project
    public ProjectResponse createProject(CreateProjectRequest request) {
        // Validate course exists
        Courses course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", request.getCourseId()));
        
        // Check if group name already exists
        if (projectRepository.existsByGroupName(request.getGroupName())) {
            throw new DuplicateResourceException("Project", "groupName", request.getGroupName());
        }
        
        // Create project
        Projects project = new Projects();
        project.setCourseId(request.getCourseId());
        project.setGroupName(request.getGroupName());
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setProjectStatus(request.getProjectStatus() != null ? request.getProjectStatus() : "IN_PROGRESS");
        project.setCreatedAt(LocalDateTime.now());
        
        Projects savedProject = projectRepository.save(project);
        return convertToResponse(savedProject);
    }
    
    // Update project
    public ProjectResponse updateProject(Integer id, CreateProjectRequest request) {
        Projects project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        // Check if group name is being changed and already exists
        if (!project.getGroupName().equals(request.getGroupName()) 
                && projectRepository.existsByGroupName(request.getGroupName())) {
            throw new DuplicateResourceException("Project", "groupName", request.getGroupName());
        }
        
        // Validate course exists if being changed
        if (!project.getCourseId().equals(request.getCourseId())) {
            if (!courseRepository.existsById(request.getCourseId())) {
                throw new ResourceNotFoundException("Course", "id", request.getCourseId());
            }
            project.setCourseId(request.getCourseId());
        }
        
        project.setGroupName(request.getGroupName());
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        if (request.getProjectStatus() != null) {
            project.setProjectStatus(request.getProjectStatus());
        }
        
        Projects updatedProject = projectRepository.save(project);
        return convertToResponse(updatedProject);
    }
    
    // Update project status
    public ProjectResponse updateProjectStatus(Integer id, String status) {
        Projects project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        project.setProjectStatus(status);
        Projects updatedProject = projectRepository.save(project);
        return convertToResponse(updatedProject);
    }
    
    // Delete project
    public void deleteProject(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", "id", id);
        }
        projectRepository.deleteById(id);
    }
    
    // Convert entity to response DTO
    private ProjectResponse convertToResponse(Projects project) {
        ProjectResponse response = new ProjectResponse();
        response.setProjectId(project.getProjectId());
        response.setCourseId(project.getCourseId());
        response.setGroupName(project.getGroupName());
        response.setProjectName(project.getProjectName());
        response.setDescription(project.getDescription());
        response.setProjectStatus(project.getProjectStatus());
        response.setCreatedAt(project.getCreatedAt());
        
        // Get course name if available
        if (project.getCourse() != null) {
            response.setCourseName(project.getCourse().getCourseName());
        }
        
        // Get member count
        long memberCount = projectMemberRepository.countByProjectIdAndIsActive(project.getProjectId(), true);
        response.setMemberCount((int) memberCount);
        
        return response;
    }
}
