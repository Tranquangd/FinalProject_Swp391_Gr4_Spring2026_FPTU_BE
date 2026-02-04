package com.example.demo.service;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Helper method to convert Users entity to UserResponse DTO
    private UserResponse toUserResponse(Users user) {
        return new UserResponse(
            user.getUserId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole(),
            user.getJiraAccountId(),
            user.getGitHubUsername(),
            user.getAvatarUrl(),
            user.getIsActive()
        );
    }
    
    // Get all users
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    // Get user by ID
    public UserResponse getUserById(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toUserResponse(user);
    }
    
    // Get user by email
    public UserResponse getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return toUserResponse(user);
    }
    
    // Get user by Jira Account ID
    public UserResponse getUserByJiraAccountId(String jiraAccountId) {
        Users user = userRepository.findByJiraAccountId(jiraAccountId)
                .orElseThrow(() -> new RuntimeException("User not found with Jira Account ID: " + jiraAccountId));
        return toUserResponse(user);
    }
    
    // Get user by GitHub username
    public UserResponse getUserByGitHubUsername(String gitHubUsername) {
        Users user = userRepository.findByGitHubUsername(gitHubUsername)
                .orElseThrow(() -> new RuntimeException("User not found with GitHub username: " + gitHubUsername));
        return toUserResponse(user);
    }
    
    // Get users by role
    public List<UserResponse> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    // Get active users
    public List<UserResponse> getActiveUsers() {
        return userRepository.findByIsActiveTrue().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    // Get inactive users
    public List<UserResponse> getInactiveUsers() {
        return userRepository.findByIsActiveFalse().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    // Search users by name
    public List<UserResponse> searchUsersByName(String name) {
        return userRepository.searchByFullName(name).stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
    
    // Create new user
    public UserResponse createUser(CreateUserRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        
        // Create entity from DTO
        Users user = new Users();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // TODO: Should hash password here!
        user.setRole(request.getRole());
        user.setJiraAccountId(request.getJiraAccountId());
        user.setGitHubUsername(request.getGitHubUsername());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setIsActive(true);
        
        Users savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }
    
    // Update user
    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Update only non-null fields from DTO
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            // Check if new email already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getJiraAccountId() != null) {
            user.setJiraAccountId(request.getJiraAccountId());
        }
        if (request.getGitHubUsername() != null) {
            user.setGitHubUsername(request.getGitHubUsername());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }
        
        Users updatedUser = userRepository.save(user);
        return toUserResponse(updatedUser);
    }
    
    // Delete user
    public void deleteUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    // Soft delete (deactivate user)
    public UserResponse deactivateUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(false);
        Users deactivatedUser = userRepository.save(user);
        return toUserResponse(deactivatedUser);
    }
    
    // Activate user
    public UserResponse activateUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(true);
        Users activatedUser = userRepository.save(user);
        return toUserResponse(activatedUser);
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
