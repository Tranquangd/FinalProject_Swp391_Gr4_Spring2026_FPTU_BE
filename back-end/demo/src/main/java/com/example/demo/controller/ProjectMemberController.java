package com.example.demo.controller;

import com.example.demo.dto.request.AddProjectMemberRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProjectMemberResponse;
import com.example.demo.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@CrossOrigin(origins = "*")
public class ProjectMemberController {
    
    @Autowired
    private ProjectMemberService projectMemberService;
    
    // GET /api/projects/{projectId}/members - Get all project members
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getProjectMembers(@PathVariable Integer projectId) {
        List<ProjectMemberResponse> members = projectMemberService.getProjectMembers(projectId);
        return ResponseEntity.ok(ApiResponse.success("Members retrieved successfully", members));
    }
    
    // GET /api/projects/{projectId}/members/active - Get active members
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getActiveMembers(@PathVariable Integer projectId) {
        List<ProjectMemberResponse> members = projectMemberService.getActiveProjectMembers(projectId);
        return ResponseEntity.ok(ApiResponse.success("Active members retrieved successfully", members));
    }
    
    // GET /api/projects/{projectId}/members/leader - Get team leader
    @GetMapping("/leader")
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> getTeamLeader(@PathVariable Integer projectId) {
        ProjectMemberResponse leader = projectMemberService.getTeamLeader(projectId);
        return ResponseEntity.ok(ApiResponse.success("Team leader retrieved successfully", leader));
    }
    
    // POST /api/projects/{projectId}/members - Add member to project
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> addMember(
            @PathVariable Integer projectId,
            @Valid @RequestBody AddProjectMemberRequest request) {
        ProjectMemberResponse member = projectMemberService.addProjectMember(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member added successfully", member));
    }
    
    // PUT /api/projects/{projectId}/members/{userId}/role - Update member role
    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> updateMemberRole(
            @PathVariable Integer projectId,
            @PathVariable Integer userId,
            @RequestParam String role) {
        ProjectMemberResponse member = projectMemberService.updateMemberRole(projectId, userId, role);
        return ResponseEntity.ok(ApiResponse.success("Member role updated successfully", member));
    }
    
    // PATCH /api/projects/{projectId}/members/{userId}/deactivate - Deactivate member
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateMember(
            @PathVariable Integer projectId,
            @PathVariable Integer userId) {
        projectMemberService.deactivateMember(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("Member deactivated successfully", null));
    }
    
    // PATCH /api/projects/{projectId}/members/{userId}/activate - Activate member
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<Void>> activateMember(
            @PathVariable Integer projectId,
            @PathVariable Integer userId) {
        projectMemberService.activateMember(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("Member activated successfully", null));
    }
    
    // DELETE /api/projects/{projectId}/members/{userId} - Remove member
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(
            @PathVariable Integer projectId,
            @PathVariable Integer userId) {
        projectMemberService.removeMember(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("Member removed successfully", null));
    }
    
    // GET /api/projects/{projectId}/members/{userId}/is-leader - Check if user is leader
    @GetMapping("/{userId}/is-leader")
    public ResponseEntity<ApiResponse<Boolean>> isTeamLeader(
            @PathVariable Integer projectId,
            @PathVariable Integer userId) {
        boolean isLeader = projectMemberService.isTeamLeader(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("Check completed", isLeader));
    }
}
