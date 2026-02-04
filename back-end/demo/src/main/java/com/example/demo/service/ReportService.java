package com.example.demo.service;

import com.example.demo.dto.response.MemberStatisticsResponse;
import com.example.demo.dto.response.ProjectMemberResponse;
import com.example.demo.dto.response.ProjectReportResponse;
import com.example.demo.entity.Users;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private JiraIssueRepository jiraIssueRepository;
    
    @Autowired
    private GithubCommitRepository githubCommitRepository;
    
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProjectMemberService projectMemberService;
    
    // Generate comprehensive project report
    public ProjectReportResponse generateProjectReport(Integer projectId) {
        // Validate project exists
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        
        ProjectReportResponse report = new ProjectReportResponse();
        report.setProjectId(projectId);
        report.setProjectName(project.getProjectName());
        report.setGroupName(project.getGroupName());
        
        // Get issue statistics
        List<Object[]> statusSummary = jiraIssueRepository.getStatusSummaryByProject(projectId);
        Map<String, Long> issuesByStatus = new HashMap<>();
        long totalIssues = 0;
        for (Object[] row : statusSummary) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            issuesByStatus.put(status, count);
            totalIssues += count;
        }
        report.setTotalIssues(totalIssues);
        report.setIssuesByStatus(issuesByStatus);
        
        // Get commit statistics
        List<Object[]> commitStats = githubCommitRepository.getCommitStatsByProject(projectId);
        Map<String, Long> commitsByMember = new HashMap<>();
        Map<String, Integer> additionsByMember = new HashMap<>();
        Map<String, Integer> deletionsByMember = new HashMap<>();
        long totalCommits = 0;
        
        for (Object[] row : commitStats) {
            String email = (String) row[0];
            Long commits = (Long) row[1];
            Long additions = (Long) row[2];
            Long deletions = (Long) row[3];
            
            commitsByMember.put(email, commits);
            additionsByMember.put(email, additions != null ? additions.intValue() : 0);
            deletionsByMember.put(email, deletions != null ? deletions.intValue() : 0);
            totalCommits += commits;
        }
        
        report.setTotalCommits(totalCommits);
        report.setCommitsByMember(commitsByMember);
        report.setAdditionsByMember(additionsByMember);
        report.setDeletionsByMember(deletionsByMember);
        
        // Get project members
        List<ProjectMemberResponse> members = projectMemberService.getActiveProjectMembers(projectId);
        report.setMembers(members);
        
        return report;
    }
    
    // Generate statistics for a specific member
    public MemberStatisticsResponse generateMemberStatistics(Integer projectId, Integer userId) {
        // Validate user exists
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Validate user is member of project
        if (!projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new ResourceNotFoundException("User is not a member of this project", "userId", userId);
        }
        
        MemberStatisticsResponse stats = new MemberStatisticsResponse();
        stats.setUserId(userId);
        stats.setFullName(user.getFullName());
        stats.setEmail(user.getEmail());
        stats.setJiraAccountId(user.getJiraAccountId());
        stats.setGitHubUsername(user.getGitHubUsername());
        
        // Task statistics
        if (user.getJiraAccountId() != null) {
            List<com.example.demo.entity.JiraIssues> assignedIssues = 
                jiraIssueRepository.findByProjectIdAndAssigneeJiraId(projectId, user.getJiraAccountId());
            
            stats.setTotalTasksAssigned((long) assignedIssues.size());
            stats.setTasksCompleted(assignedIssues.stream()
                .filter(issue -> "Done".equalsIgnoreCase(issue.getStatus()) || 
                               "Closed".equalsIgnoreCase(issue.getStatus()))
                .count());
            stats.setTasksInProgress(assignedIssues.stream()
                .filter(issue -> "In Progress".equalsIgnoreCase(issue.getStatus()))
                .count());
            
            int totalStoryPoints = assignedIssues.stream()
                .filter(issue -> issue.getStoryPoint() != null)
                .mapToInt(com.example.demo.entity.JiraIssues::getStoryPoint)
                .sum();
            stats.setTotalStoryPoints(totalStoryPoints);
            
            int completedStoryPoints = assignedIssues.stream()
                .filter(issue -> issue.getStoryPoint() != null && 
                               ("Done".equalsIgnoreCase(issue.getStatus()) || 
                                "Closed".equalsIgnoreCase(issue.getStatus())))
                .mapToInt(com.example.demo.entity.JiraIssues::getStoryPoint)
                .sum();
            stats.setCompletedStoryPoints(completedStoryPoints);
            
            // Calculate completion rate
            if (stats.getTotalTasksAssigned() > 0) {
                double completionRate = (stats.getTasksCompleted() * 100.0) / stats.getTotalTasksAssigned();
                stats.setTaskCompletionRate(Math.round(completionRate * 100.0) / 100.0);
            } else {
                stats.setTaskCompletionRate(0.0);
            }
        }
        
        // Commit statistics
        if (user.getEmail() != null) {
            List<Object[]> commitData = githubCommitRepository.getCommitStatsByProject(projectId);
            for (Object[] row : commitData) {
                String email = (String) row[0];
                if (user.getEmail().equalsIgnoreCase(email)) {
                    Long commits = (Long) row[1];
                    Long additions = (Long) row[2];
                    Long deletions = (Long) row[3];
                    
                    stats.setTotalCommits(commits);
                    stats.setTotalAdditions(additions != null ? additions.intValue() : 0);
                    stats.setTotalDeletions(deletions != null ? deletions.intValue() : 0);
                    stats.setNetChanges(stats.getTotalAdditions() - stats.getTotalDeletions());
                    break;
                }
            }
        }
        
        if (stats.getTotalCommits() == null) {
            stats.setTotalCommits(0L);
            stats.setTotalAdditions(0);
            stats.setTotalDeletions(0);
            stats.setNetChanges(0);
        }
        
        return stats;
    }
    
    // Get all member statistics for a project
    public List<MemberStatisticsResponse> generateAllMemberStatistics(Integer projectId) {
        List<ProjectMemberResponse> members = projectMemberService.getActiveProjectMembers(projectId);
        
        return members.stream()
                .map(member -> generateMemberStatistics(projectId, member.getUserId()))
                .collect(Collectors.toList());
    }
    
    // Get lecturer's overview (all projects under their courses)
    public List<ProjectReportResponse> generateLecturerOverview(Integer lecturerId) {
        List<com.example.demo.entity.Projects> projects = projectRepository.findByLecturerId(lecturerId);
        
        return projects.stream()
                .map(project -> generateProjectReport(project.getProjectId()))
                .collect(Collectors.toList());
    }
}
