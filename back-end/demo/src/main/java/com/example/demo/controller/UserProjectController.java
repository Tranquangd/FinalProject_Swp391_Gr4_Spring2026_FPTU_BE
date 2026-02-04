package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProjectMemberResponse;
import com.example.demo.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/projects")
@CrossOrigin(origins = "*")
public class UserProjectController {
    
    @Autowired
    private ProjectMemberService projectMemberService;
    
    // GET /api/users/{userId}/projects - Get all projects for a user
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getUserProjects(@PathVariable Integer userId) {
        List<ProjectMemberResponse> projects = projectMemberService.getUserProjects(userId);
        return ResponseEntity.ok(ApiResponse.success("User projects retrieved successfully", projects));
    }
}
