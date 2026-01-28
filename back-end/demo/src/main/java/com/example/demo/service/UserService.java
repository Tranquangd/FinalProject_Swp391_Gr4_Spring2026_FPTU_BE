package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Get all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get user by ID
    public Optional<Users> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    
    // Get user by email
    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // Get user by Jira Account ID
    public Optional<Users> getUserByJiraAccountId(String jiraAccountId) {
        return userRepository.findByJiraAccountId(jiraAccountId);
    }
    
    // Get user by GitHub username
    public Optional<Users> getUserByGitHubUsername(String gitHubUsername) {
        return userRepository.findByGitHubUsername(gitHubUsername);
    }
    
    // Get users by role
    public List<Users> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
    // Get active users
    public List<Users> getActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }
    
    // Get inactive users
    public List<Users> getInactiveUsers() {
        return userRepository.findByIsActiveFalse();
    }
    
    // Search users by name
    public List<Users> searchUsersByName(String name) {
        return userRepository.searchByFullName(name);
    }
    
    // Create new user
    public Users createUser(Users user) {
        // Check if email already exists
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        
        // Set default values
        if (user.getIsActive() == null) {
            user.setIsActive(true);
        }
        
        return userRepository.save(user);
    }
    
    // Update user
    public Users updateUser(Integer id, Users userDetails) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Update fields
        if (userDetails.getFullName() != null) {
            user.setFullName(userDetails.getFullName());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            // Check if new email already exists
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new RuntimeException("Email already exists: " + userDetails.getEmail());
            }
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPasswordHash() != null) {
            user.setPasswordHash(userDetails.getPasswordHash());
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        if (userDetails.getJiraAccountId() != null) {
            user.setJiraAccountId(userDetails.getJiraAccountId());
        }
        if (userDetails.getGitHubUsername() != null) {
            user.setGitHubUsername(userDetails.getGitHubUsername());
        }
        if (userDetails.getAvatarUrl() != null) {
            user.setAvatarUrl(userDetails.getAvatarUrl());
        }
        if (userDetails.getIsActive() != null) {
            user.setIsActive(userDetails.getIsActive());
        }
        
        return userRepository.save(user);
    }
    
    // Delete user
    public void deleteUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    // Soft delete (deactivate user)
    public Users deactivateUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(false);
        return userRepository.save(user);
    }
    
    // Activate user
    public Users activateUser(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setIsActive(true);
        return userRepository.save(user);
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
