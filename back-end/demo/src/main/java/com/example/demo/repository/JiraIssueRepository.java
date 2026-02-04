package com.example.demo.repository;

import com.example.demo.entity.JiraIssues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JiraIssueRepository extends JpaRepository<JiraIssues, Integer> {
    
    // Find by project ID
    List<JiraIssues> findByProjectId(Integer projectId);
    
    // Find by sprint ID
    List<JiraIssues> findBySprintId(Integer sprintId);
    
    // Find by Jira key
    Optional<JiraIssues> findByJiraKey(String jiraKey);
    
    // Find by assignee Jira ID
    List<JiraIssues> findByAssigneeJiraId(String assigneeJiraId);
    
    // Find by status
    List<JiraIssues> findByStatus(String status);
    
    // Find by project and status
    List<JiraIssues> findByProjectIdAndStatus(Integer projectId, String status);
    
    // Find by priority
    List<JiraIssues> findByPriority(String priority);
    
    // Find by issue type
    List<JiraIssues> findByIssueType(String issueType);
    
    // Find by project and assignee
    List<JiraIssues> findByProjectIdAndAssigneeJiraId(Integer projectId, String assigneeJiraId);
    
    // Find issues assigned to a user in a specific project
    @Query("SELECT j FROM JiraIssues j WHERE j.projectId = :projectId AND j.assigneeJiraId = :assigneeJiraId ORDER BY j.priority DESC, j.lastSyncDate DESC")
    List<JiraIssues> findAssignedIssuesInProject(@Param("projectId") Integer projectId, @Param("assigneeJiraId") String assigneeJiraId);
    
    // Get issues summary by project (count by status)
    @Query("SELECT j.status, COUNT(j) FROM JiraIssues j WHERE j.projectId = :projectId GROUP BY j.status")
    List<Object[]> getStatusSummaryByProject(@Param("projectId") Integer projectId);
    
    // Get issues for SRS document (all requirements/stories)
    @Query("SELECT j FROM JiraIssues j WHERE j.projectId = :projectId AND j.issueType IN ('Story', 'Epic', 'Requirement') ORDER BY j.priority DESC")
    List<JiraIssues> findRequirementsForSrs(@Param("projectId") Integer projectId);
    
    // Count by assignee in project
    @Query("SELECT COUNT(j) FROM JiraIssues j WHERE j.projectId = :projectId AND j.assigneeJiraId = :assigneeJiraId")
    long countByProjectAndAssignee(@Param("projectId") Integer projectId, @Param("assigneeJiraId") String assigneeJiraId);
    
    // Find recently synced issues
    @Query("SELECT j FROM JiraIssues j WHERE j.lastSyncDate >= :since ORDER BY j.lastSyncDate DESC")
    List<JiraIssues> findRecentlySynced(@Param("since") LocalDateTime since);
    
    // Check if Jira key exists
    boolean existsByJiraKey(String jiraKey);
}
