package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.JiraIssueResponse;
import com.example.demo.service.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jira/issues")
@CrossOrigin(origins = "*")
public class JiraIssueController {
    
    @Autowired
    private JiraIssueService jiraIssueService;
    
    // GET /api/jira/issues - Get all issues
    @GetMapping
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getAllIssues() {
        List<JiraIssueResponse> issues = jiraIssueService.getAllIssues();
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/{id} - Get issue by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JiraIssueResponse>> getIssueById(@PathVariable Integer id) {
        JiraIssueResponse issue = jiraIssueService.getIssueById(id);
        return ResponseEntity.ok(ApiResponse.success("Issue retrieved successfully", issue));
    }
    
    // GET /api/jira/issues/project/{projectId} - Get issues by project
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getIssuesByProject(@PathVariable Integer projectId) {
        List<JiraIssueResponse> issues = jiraIssueService.getIssuesByProject(projectId);
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/sprint/{sprintId} - Get issues by sprint
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getIssuesBySprint(@PathVariable Integer sprintId) {
        List<JiraIssueResponse> issues = jiraIssueService.getIssuesBySprint(sprintId);
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/assignee/{assigneeJiraId} - Get issues by assignee
    @GetMapping("/assignee/{assigneeJiraId}")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getIssuesByAssignee(@PathVariable String assigneeJiraId) {
        List<JiraIssueResponse> issues = jiraIssueService.getIssuesByAssignee(assigneeJiraId);
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/status/{status} - Get issues by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getIssuesByStatus(@PathVariable String status) {
        List<JiraIssueResponse> issues = jiraIssueService.getIssuesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/project/{projectId}/assignee/{assigneeJiraId} - Get issues by project and assignee
    @GetMapping("/project/{projectId}/assignee/{assigneeJiraId}")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getIssuesByProjectAndAssignee(
            @PathVariable Integer projectId,
            @PathVariable String assigneeJiraId) {
        List<JiraIssueResponse> issues = jiraIssueService.getIssuesByProjectAndAssignee(projectId, assigneeJiraId);
        return ResponseEntity.ok(ApiResponse.success("Issues retrieved successfully", issues));
    }
    
    // GET /api/jira/issues/project/{projectId}/srs - Get requirements for SRS document
    @GetMapping("/project/{projectId}/srs")
    public ResponseEntity<ApiResponse<List<JiraIssueResponse>>> getRequirementsForSrs(@PathVariable Integer projectId) {
        List<JiraIssueResponse> requirements = jiraIssueService.getRequirementsForSrs(projectId);
        return ResponseEntity.ok(ApiResponse.success("Requirements retrieved successfully", requirements));
    }
}
