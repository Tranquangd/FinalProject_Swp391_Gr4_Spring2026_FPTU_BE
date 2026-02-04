package com.example.demo.service;

import com.example.demo.dto.request.AddProjectMemberRequest;
import com.example.demo.dto.response.ProjectMemberResponse;
import com.example.demo.entity.ProjectMembers;
import com.example.demo.entity.Users;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProjectMemberRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectMemberService {
    
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Get all members of a project
    public List<ProjectMemberResponse> getProjectMembers(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        
        return projectMemberRepository.findByProjectId(projectId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get active members of a project
    public List<ProjectMemberResponse> getActiveProjectMembers(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        
        return projectMemberRepository.findByProjectIdAndIsActive(projectId, true).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get team leader of a project
    public ProjectMemberResponse getTeamLeader(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        
        ProjectMembers leader = projectMemberRepository.findTeamLeader(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Team leader not found for project", "projectId", projectId));
        
        return convertToResponse(leader);
    }
    
    // Get all projects for a user
    public List<ProjectMemberResponse> getUserProjects(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        
        return projectMemberRepository.findActiveProjectsByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Add member to project
    public ProjectMemberResponse addProjectMember(Integer projectId, AddProjectMemberRequest request) {
        // Validate project exists
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        
        // Validate user exists
        if (!userRepository.existsById(request.getUserId())) {
            throw new ResourceNotFoundException("User", "id", request.getUserId());
        }
        
        // Check if member already exists in project
        if (projectMemberRepository.existsByProjectIdAndUserId(projectId, request.getUserId())) {
            throw new DuplicateResourceException("User already exists in this project", "userId", request.getUserId());
        }
        
        // If adding as LEADER, check if leader already exists
        if ("LEADER".equals(request.getRole())) {
            Optional<ProjectMembers> existingLeader = projectMemberRepository.findTeamLeader(projectId);
            if (existingLeader.isPresent()) {
                throw new BadRequestException("Project already has a team leader. Remove existing leader first.");
            }
        }
        
        // Create project member
        ProjectMembers projectMember = new ProjectMembers();
        projectMember.setProjectId(projectId);
        projectMember.setUserId(request.getUserId());
        projectMember.setRole(request.getRole());
        projectMember.setIsActive(true);
        
        ProjectMembers savedMember = projectMemberRepository.save(projectMember);
        return convertToResponse(savedMember);
    }
    
    // Update member role
    public ProjectMemberResponse updateMemberRole(Integer projectId, Integer userId, String newRole) {
        ProjectMembers member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found", "userId", userId));
        
        // If changing to LEADER, check if leader already exists
        if ("LEADER".equals(newRole) && !"LEADER".equals(member.getRole())) {
            Optional<ProjectMembers> existingLeader = projectMemberRepository.findTeamLeader(projectId);
            if (existingLeader.isPresent() && !existingLeader.get().getUserId().equals(userId)) {
                throw new BadRequestException("Project already has a team leader. Remove existing leader first.");
            }
        }
        
        member.setRole(newRole);
        ProjectMembers updatedMember = projectMemberRepository.save(member);
        return convertToResponse(updatedMember);
    }
    
    // Deactivate member (soft delete)
    public void deactivateMember(Integer projectId, Integer userId) {
        ProjectMembers member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found", "userId", userId));
        
        member.setIsActive(false);
        projectMemberRepository.save(member);
    }
    
    // Activate member
    public void activateMember(Integer projectId, Integer userId) {
        ProjectMembers member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found", "userId", userId));
        
        member.setIsActive(true);
        projectMemberRepository.save(member);
    }
    
    // Remove member from project (hard delete)
    public void removeMember(Integer projectId, Integer userId) {
        ProjectMembers member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project member not found", "userId", userId));
        
        projectMemberRepository.delete(member);
    }
    
    // Check if user is team leader of a project
    public boolean isTeamLeader(Integer projectId, Integer userId) {
        Optional<ProjectMembers> member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId);
        return member.isPresent() && "LEADER".equals(member.get().getRole()) && member.get().getIsActive();
    }
    
    // Check if user is member of a project
    public boolean isMemberOfProject(Integer projectId, Integer userId) {
        return projectMemberRepository.existsByProjectIdAndUserId(projectId, userId);
    }
    
    // Convert entity to response DTO
    private ProjectMemberResponse convertToResponse(ProjectMembers member) {
        ProjectMemberResponse response = new ProjectMemberResponse();
        response.setProjectId(member.getProjectId());
        response.setUserId(member.getUserId());
        response.setRole(member.getRole());
        response.setIsActive(member.getIsActive());
        
        // Get user details if available
        if (member.getUser() != null) {
            Users user = member.getUser();
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setJiraAccountId(user.getJiraAccountId());
            response.setGitHubUsername(user.getGitHubUsername());
        } else {
            // Fetch user details manually if relationship not loaded
            userRepository.findById(member.getUserId()).ifPresent(user -> {
                response.setFullName(user.getFullName());
                response.setEmail(user.getEmail());
                response.setJiraAccountId(user.getJiraAccountId());
                response.setGitHubUsername(user.getGitHubUsername());
            });
        }
        
        return response;
    }
}
