package com.example.demo.service;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Users testUser;
    private CreateUserRequest createRequest;
    private UpdateUserRequest updateRequest;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        userRepository.deleteAll();
        
        // Create test user in database
        testUser = new Users();
        testUser.setFullName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser.setRole("STUDENT");
        testUser.setIsActive(true);
        testUser.setJiraAccountId("jira123");
        testUser.setGitHubUsername("johndoe");
        testUser = userRepository.save(testUser);

        // Setup test requests
        createRequest = new CreateUserRequest();
        createRequest.setFullName("Jane Smith");
        createRequest.setEmail("jane@example.com");
        createRequest.setPassword("password123");
        createRequest.setRole("STUDENT");

        updateRequest = new UpdateUserRequest();
        updateRequest.setFullName("John Updated");
        updateRequest.setEmail("john.updated@example.com");
        updateRequest.setRole("LECTURER");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    // ==================== CREATE USER ====================
    @Test
    void createUser_WithValidData_ShouldReturnUserResponse() {
        UserResponse result = userService.createUser(createRequest);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getFullName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("STUDENT", result.getRole());
        // Password should not be exposed in UserResponse
    }

    // ==================== GET ALL USERS ====================
    @Test
    void getAllUsers_ShouldReturnAllUserResponses() {
        List<UserResponse> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getFullName());
        // Password should not be exposed in UserResponse
    }

    // ==================== GET USER BY ID ====================
    @Test
    void getUserById_WithValidId_ShouldReturnUserResponse() {
        UserResponse result = userService.getUserById(testUser.getUserId());

        assertNotNull(result);
        assertEquals(testUser.getUserId(), result.getUserId());
        assertEquals("John Doe", result.getFullName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getUserById_WithInvalidId_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getUserById(99999));
        
        assertEquals("User not found with id: 99999", exception.getMessage());
    }

    // ==================== GET USER BY EMAIL ====================
    @Test
    void getUserByEmail_WithValidEmail_ShouldReturnUserResponse() {
        UserResponse result = userService.getUserByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getUserByEmail_WithInvalidEmail_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getUserByEmail("invalid@example.com"));
        
        assertEquals("User not found with email: invalid@example.com", exception.getMessage());
    }

    // ==================== UPDATE USER ====================
    @Test
    void updateUser_WithValidData_ShouldReturnUpdatedUserResponse() {
        UserResponse result = userService.updateUser(testUser.getUserId(), updateRequest);

        assertNotNull(result);
        assertEquals("John Updated", result.getFullName());
        assertEquals("john.updated@example.com", result.getEmail());
        assertEquals("LECTURER", result.getRole());
    }

    @Test
    void updateUser_WithInvalidId_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.updateUser(99999, updateRequest));
        
        assertEquals("User not found with id: 99999", exception.getMessage());
    }

    // ==================== DELETE USER ====================
    @Test
    void deleteUser_WithValidId_ShouldDeleteUser() {
        userService.deleteUser(testUser.getUserId());

        assertFalse(userRepository.existsById(testUser.getUserId()));
    }

    @Test
    void deleteUser_WithInvalidId_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.deleteUser(99999));
        
        assertEquals("User not found with id: 99999", exception.getMessage());
    }

    // ==================== GET USERS BY ROLE ====================
    @Test
    void getUsersByRole_ShouldReturnMatchingUsers() {
        List<UserResponse> result = userService.getUsersByRole("STUDENT");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("STUDENT", result.get(0).getRole());
    }

    @Test
    void getUsersByRole_WithNoMatches_ShouldReturnEmptyList() {
        List<UserResponse> result = userService.getUsersByRole("ADMIN");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ==================== GET ACTIVE USERS ====================
    @Test
    void getActiveUsers_ShouldReturnOnlyActiveUsers() {
        // Create inactive user
        Users inactiveUser = new Users();
        inactiveUser.setFullName("Inactive User");
        inactiveUser.setEmail("inactive@test.com");
        inactiveUser.setPasswordHash("hash");
        inactiveUser.setRole("STUDENT");
        inactiveUser.setIsActive(false);
        userRepository.save(inactiveUser);

        List<UserResponse> result = userService.getActiveUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertTrue(result.get(0).getIsActive());
    }

    // ==================== GET USER BY JIRA ACCOUNT ID ====================
    @Test
    void getUserByJiraAccountId_WithValidId_ShouldReturnUserResponse() {
        UserResponse result = userService.getUserByJiraAccountId("jira123");

        assertNotNull(result);
        assertEquals("jira123", result.getJiraAccountId());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getUserByJiraAccountId_WithInvalidId_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getUserByJiraAccountId("invalid_jira_id"));
        
        assertEquals("User not found with Jira Account ID: invalid_jira_id", exception.getMessage());
    }

    // ==================== GET USER BY GITHUB USERNAME ====================
    @Test
    void getUserByGitHubUsername_WithValidUsername_ShouldReturnUserResponse() {
        UserResponse result = userService.getUserByGitHubUsername("johndoe");

        assertNotNull(result);
        assertEquals("johndoe", result.getGitHubUsername());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getUserByGitHubUsername_WithInvalidUsername_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> userService.getUserByGitHubUsername("invalid_github_user"));
        
        assertEquals("User not found with GitHub username: invalid_github_user", exception.getMessage());
    }
}
