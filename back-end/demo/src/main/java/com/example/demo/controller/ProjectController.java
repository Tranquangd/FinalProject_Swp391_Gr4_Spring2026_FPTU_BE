package com.example.demo.controller;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    // GET /api/projects - Get all projects
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", projects));
    }
    
    // GET /api/projects/{id} - Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Integer id) {
        ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("Project retrieved successfully", project));
    }
    
    // GET /api/projects/course/{courseId} - Get projects by course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByCourse(@PathVariable Integer courseId) {
        List<ProjectResponse> projects = projectService.getProjectsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", projects));
    }
    
    // GET /api/projects/lecturer/{lecturerId} - Get projects by lecturer
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByLecturer(@PathVariable Integer lecturerId) {
        List<ProjectResponse> projects = projectService.getProjectsByLecturer(lecturerId);
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", projects));
    }
    
    // GET /api/projects/status/{status} - Get projects by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getProjectsByStatus(@PathVariable String status) {
        List<ProjectResponse> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", projects));
    }
    
    // GET /api/projects/search?name={name} - Search projects by name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> searchProjects(@RequestParam String name) {
        List<ProjectResponse> projects = projectService.searchProjectsByName(name);
        return ResponseEntity.ok(ApiResponse.success("Projects found", projects));
    }
    
    // POST /api/projects - Create new project
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse project = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created successfully", project));
    }
    
    // PUT /api/projects/{id} - Update project
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Integer id,
            @Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse project = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", project));
    }
    
    // PATCH /api/projects/{id}/status - Update project status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProjectStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        ProjectResponse project = projectService.updateProjectStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Project status updated successfully", project));
    }
    
    // DELETE /api/projects/{id} - Delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully", null));
    }
}
