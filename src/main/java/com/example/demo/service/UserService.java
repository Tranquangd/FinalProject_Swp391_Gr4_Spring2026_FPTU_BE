package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
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
    
    // Register new user
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        
        // Hash password
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        
        // Create new user
        Users user = new Users();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setRole(request.getRole() != null ? request.getRole().toUpperCase() : "STUDENT");
        user.setIsActive(true);
        
        // Save user
        Users savedUser = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getUserId(), savedUser.getRole());
        
        // Return auth response
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getUserId(), 
                               savedUser.getFullName(), savedUser.getRole());
    }
    
    // Login user
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        Optional<Users> userOpt = userRepository.findByEmail(request.getEmail());
        
        if (userOpt.isEmpty()) {
            return new AuthResponse("Invalid email or password");
        }
        
        Users user = userOpt.get();
        
        // Check if user is active
        if (user.getIsActive() == null || !user.getIsActive()) {
            return new AuthResponse("Account is deactivated. Please contact administrator.");
        }
        
        // Check password
        if (user.getPasswordHash() == null || 
            !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return new AuthResponse("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserId(), user.getRole());
        
        // Return auth response
        return new AuthResponse(token, user.getEmail(), user.getUserId(), 
                               user.getFullName(), user.getRole());
    }
}
