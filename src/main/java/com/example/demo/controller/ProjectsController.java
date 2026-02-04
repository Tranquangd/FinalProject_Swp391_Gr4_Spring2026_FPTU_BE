package com.example.demo.controller;

import com.example.demo.entity.Projects;
import com.example.demo.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectsController {
    
    @Autowired
    private ProjectsService projectsService;
    
    // GET /api/projects - Get all projects
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        try {
            List<Projects> projects = projectsService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get projects: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/projects/{id} - Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Integer id) {
        try {
            Optional<Projects> project = projectsService.getProjectById(id);
            if (project.isPresent()) {
                return ResponseEntity.ok(project.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Project not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get project: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/projects/course/{courseId} - Get projects by course ID
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getProjectsByCourseId(@PathVariable Integer courseId) {
        try {
            List<Projects> projects = projectsService.getProjectsByCourseId(courseId);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get projects: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/projects/status/{status} - Get projects by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getProjectsByStatus(@PathVariable String status) {
        try {
            List<Projects> projects = projectsService.getProjectsByStatus(status);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get projects: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/projects/search?name={name} - Search projects by name
    @GetMapping("/search")
    public ResponseEntity<?> searchProjects(@RequestParam String name) {
        try {
            List<Projects> projects = projectsService.searchProjectsByName(name);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to search projects: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // POST /api/projects - Create new project
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Projects project) {
        try {
            Projects createdProject = projectsService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create project: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // PUT /api/projects/{id} - Update project
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody Projects projectDetails) {
        try {
            Projects updatedProject = projectsService.updateProject(id, projectDetails);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update project: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // DELETE /api/projects/{id} - Delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        try {
            projectsService.deleteProject(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Project deleted successfully");
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete project: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
