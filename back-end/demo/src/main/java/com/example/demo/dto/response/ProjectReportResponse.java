package com.example.demo.dto.response;

import java.util.List;
import java.util.Map;

public class ProjectReportResponse {
    
    private Integer projectId;
    private String projectName;
    private String groupName;
    
    // Task/Issue statistics
    private Long totalIssues;
    private Map<String, Long> issuesByStatus; // "To Do": 5, "In Progress": 3, "Done": 10
    private Map<String, Long> issuesByPriority;
    private Map<String, Integer> storyPointsByMember;
    
    // Commit statistics
    private Long totalCommits;
    private Map<String, Long> commitsByMember;
    private Map<String, Integer> additionsByMember;
    private Map<String, Integer> deletionsByMember;
    
    // Member list
    private List<ProjectMemberResponse> members;
    
    public ProjectReportResponse() {}
    
    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public Long getTotalIssues() {
        return totalIssues;
    }
    
    public void setTotalIssues(Long totalIssues) {
        this.totalIssues = totalIssues;
    }
    
    public Map<String, Long> getIssuesByStatus() {
        return issuesByStatus;
    }
    
    public void setIssuesByStatus(Map<String, Long> issuesByStatus) {
        this.issuesByStatus = issuesByStatus;
    }
    
    public Map<String, Long> getIssuesByPriority() {
        return issuesByPriority;
    }
    
    public void setIssuesByPriority(Map<String, Long> issuesByPriority) {
        this.issuesByPriority = issuesByPriority;
    }
    
    public Map<String, Integer> getStoryPointsByMember() {
        return storyPointsByMember;
    }
    
    public void setStoryPointsByMember(Map<String, Integer> storyPointsByMember) {
        this.storyPointsByMember = storyPointsByMember;
    }
    
    public Long getTotalCommits() {
        return totalCommits;
    }
    
    public void setTotalCommits(Long totalCommits) {
        this.totalCommits = totalCommits;
    }
    
    public Map<String, Long> getCommitsByMember() {
        return commitsByMember;
    }
    
    public void setCommitsByMember(Map<String, Long> commitsByMember) {
        this.commitsByMember = commitsByMember;
    }
    
    public Map<String, Integer> getAdditionsByMember() {
        return additionsByMember;
    }
    
    public void setAdditionsByMember(Map<String, Integer> additionsByMember) {
        this.additionsByMember = additionsByMember;
    }
    
    public Map<String, Integer> getDeletionsByMember() {
        return deletionsByMember;
    }
    
    public void setDeletionsByMember(Map<String, Integer> deletionsByMember) {
        this.deletionsByMember = deletionsByMember;
    }
    
    public List<ProjectMemberResponse> getMembers() {
        return members;
    }
    
    public void setMembers(List<ProjectMemberResponse> members) {
        this.members = members;
    }
}
