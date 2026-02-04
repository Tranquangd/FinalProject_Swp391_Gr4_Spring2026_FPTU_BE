package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.MemberStatisticsResponse;
import com.example.demo.dto.response.ProjectReportResponse;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    // GET /api/reports/project/{projectId} - Generate project report
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<ProjectReportResponse>> getProjectReport(@PathVariable Integer projectId) {
        ProjectReportResponse report = reportService.generateProjectReport(projectId);
        return ResponseEntity.ok(ApiResponse.success("Project report generated successfully", report));
    }
    
    // GET /api/reports/project/{projectId}/member/{userId} - Get member statistics
    @GetMapping("/project/{projectId}/member/{userId}")
    public ResponseEntity<ApiResponse<MemberStatisticsResponse>> getMemberStatistics(
            @PathVariable Integer projectId,
            @PathVariable Integer userId) {
        MemberStatisticsResponse stats = reportService.generateMemberStatistics(projectId, userId);
        return ResponseEntity.ok(ApiResponse.success("Member statistics generated successfully", stats));
    }
    
    // GET /api/reports/project/{projectId}/members - Get all member statistics
    @GetMapping("/project/{projectId}/members")
    public ResponseEntity<ApiResponse<List<MemberStatisticsResponse>>> getAllMemberStatistics(@PathVariable Integer projectId) {
        List<MemberStatisticsResponse> stats = reportService.generateAllMemberStatistics(projectId);
        return ResponseEntity.ok(ApiResponse.success("All member statistics generated successfully", stats));
    }
    
    // GET /api/reports/lecturer/{lecturerId} - Get lecturer overview (all projects)
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<ApiResponse<List<ProjectReportResponse>>> getLecturerOverview(@PathVariable Integer lecturerId) {
        List<ProjectReportResponse> reports = reportService.generateLecturerOverview(lecturerId);
        return ResponseEntity.ok(ApiResponse.success("Lecturer overview generated successfully", reports));
    }
}
