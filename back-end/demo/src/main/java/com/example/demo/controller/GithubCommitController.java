package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.GithubCommitResponse;
import com.example.demo.service.GithubCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/github/commits")
@CrossOrigin(origins = "*")
public class GithubCommitController {
    
    @Autowired
    private GithubCommitService githubCommitService;
    
    // GET /api/github/commits - Get all commits
    @GetMapping
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getAllCommits() {
        List<GithubCommitResponse> commits = githubCommitService.getAllCommits();
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/{id} - Get commit by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GithubCommitResponse>> getCommitById(@PathVariable Integer id) {
        GithubCommitResponse commit = githubCommitService.getCommitById(id);
        return ResponseEntity.ok(ApiResponse.success("Commit retrieved successfully", commit));
    }
    
    // GET /api/github/commits/repository/{repoId} - Get commits by repository
    @GetMapping("/repository/{repoId}")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getCommitsByRepository(@PathVariable Integer repoId) {
        List<GithubCommitResponse> commits = githubCommitService.getCommitsByRepository(repoId);
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/email/{email} - Get commits by email
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getCommitsByEmail(@PathVariable String email) {
        List<GithubCommitResponse> commits = githubCommitService.getCommitsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/repository/{repoId}/date-range - Get commits by date range
    @GetMapping("/repository/{repoId}/date-range")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getCommitsByDateRange(
            @PathVariable Integer repoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<GithubCommitResponse> commits = githubCommitService.getCommitsByDateRange(repoId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/email/{email}/date-range - Get commits by email and date range
    @GetMapping("/email/{email}/date-range")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getCommitsByEmailAndDateRange(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<GithubCommitResponse> commits = githubCommitService.getCommitsByEmailAndDateRange(email, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/repository/{repoId}/recent - Get recent commits
    @GetMapping("/repository/{repoId}/recent")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getRecentCommits(
            @PathVariable Integer repoId,
            @RequestParam(defaultValue = "10") int limit) {
        List<GithubCommitResponse> commits = githubCommitService.getRecentCommits(repoId, limit);
        return ResponseEntity.ok(ApiResponse.success("Recent commits retrieved successfully", commits));
    }
    
    // GET /api/github/commits/since - Get commits since specific date
    @GetMapping("/since")
    public ResponseEntity<ApiResponse<List<GithubCommitResponse>>> getCommitsSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        List<GithubCommitResponse> commits = githubCommitService.getCommitsSince(since);
        return ResponseEntity.ok(ApiResponse.success("Commits retrieved successfully", commits));
    }
}
