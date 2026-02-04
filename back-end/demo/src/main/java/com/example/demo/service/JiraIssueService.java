package com.example.demo.service;

import com.example.demo.dto.response.JiraIssueResponse;
import com.example.demo.entity.JiraIssues;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JiraIssueRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JiraIssueService {
    
    @Autowired
    private JiraIssueRepository jiraIssueRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Get all issues
    public List<JiraIssueResponse> getAllIssues() {
        return jiraIssueRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get issue by ID
    public JiraIssueResponse getIssueById(Integer id) {
        JiraIssues issue = jiraIssueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jira Issue", "id", id));
        return convertToResponse(issue);
    }
    
    // Get issues by project
    public List<JiraIssueResponse> getIssuesByProject(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return jiraIssueRepository.findByProjectId(projectId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get issues by sprint
    public List<JiraIssueResponse> getIssuesBySprint(Integer sprintId) {
        return jiraIssueRepository.findBySprintId(sprintId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get issues by assignee
    public List<JiraIssueResponse> getIssuesByAssignee(String assigneeJiraId) {
        return jiraIssueRepository.findByAssigneeJiraId(assigneeJiraId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get issues by status
    public List<JiraIssueResponse> getIssuesByStatus(String status) {
        return jiraIssueRepository.findByStatus(status).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get issues by project and assignee
    public List<JiraIssueResponse> getIssuesByProjectAndAssignee(Integer projectId, String assigneeJiraId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return jiraIssueRepository.findAssignedIssuesInProject(projectId, assigneeJiraId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get requirements for SRS document
    public List<JiraIssueResponse> getRequirementsForSrs(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return jiraIssueRepository.findRequirementsForSrs(projectId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Convert entity to response DTO
    private JiraIssueResponse convertToResponse(JiraIssues issue) {
        JiraIssueResponse response = new JiraIssueResponse();
        response.setIssueId(issue.getIssueId());
        response.setProjectId(issue.getProjectId());
        response.setSprintId(issue.getSprintId());
        response.setJiraKey(issue.getJiraKey());
        response.setIssueType(issue.getIssueType());
        response.setSummary(issue.getSummary());
        response.setDescription(issue.getDescription());
        response.setAcceptanceCriteria(issue.getAcceptanceCriteria());
        response.setStoryPoint(issue.getStoryPoint());
        response.setPriority(issue.getPriority());
        response.setStatus(issue.getStatus());
        response.setAssigneeJiraId(issue.getAssigneeJiraId());
        response.setLastSyncDate(issue.getLastSyncDate());
        
        // Get sprint name if available
        if (issue.getSprint() != null) {
            response.setSprintName(issue.getSprint().getSprintName());
        }
        
        // Get assignee name if available
        if (issue.getAssigneeJiraId() != null) {
            userRepository.findByJiraAccountId(issue.getAssigneeJiraId())
                .ifPresent(user -> response.setAssigneeName(user.getFullName()));
        }
        
        return response;
    }
}
