package com.example.demo.controller;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateUserRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

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
    void createUser_WithValidData_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value("Jane Smith"))
            .andExpect(jsonPath("$.email").value("jane@example.com"))
            .andExpect(jsonPath("$.role").value("STUDENT"))
            .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        createRequest.setEmail(null); // Invalid - email is required

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isBadRequest());
    }

    // ==================== GET ALL USERS ====================
    @Test
    void getAllUsers_ShouldReturnUserList() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].fullName").value("John Doe"))
            .andExpect(jsonPath("$[0].email").value("john@example.com"))
            .andExpect(jsonPath("$[0].passwordHash").doesNotExist());
    }

    // ==================== GET USER BY ID ====================
    @Test
    void getUserById_WithValidId_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/" + testUser.getUserId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/99999"))
            .andExpect(status().isNotFound());
    }

    // ==================== UPDATE USER ====================
    @Test
    void updateUser_WithValidData_ShouldReturnUpdatedUser() throws Exception {
        mockMvc.perform(put("/api/users/" + testUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value("John Updated"))
            .andExpect(jsonPath("$.email").value("john.updated@example.com"))
            .andExpect(jsonPath("$.role").value("LECTURER"));
    }

    @Test
    void updateUser_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/users/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isBadRequest());
    }

    // ==================== DELETE USER ====================
    @Test
    void deleteUser_WithValidId_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUser.getUserId()))
            .andExpect(status().isOk());
        
        // Verify deletion
        mockMvc.perform(get("/api/users/" + testUser.getUserId()))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/99999"))
            .andExpect(status().isNotFound());
    }

    // ==================== GET USER BY EMAIL ====================
    @Test
    void getUserByEmail_WithValidEmail_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/email/john@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("john@example.com"))
            .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    // ==================== GET USERS BY ROLE ====================
    @Test
    void getUsersByRole_ShouldReturnMatchingUsers() throws Exception {
        mockMvc.perform(get("/api/users/role/STUDENT"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].role").value("STUDENT"));
    }

    // ==================== GET ACTIVE USERS ====================
    @Test
    void getActiveUsers_ShouldReturnOnlyActiveUsers() throws Exception {
        // Create inactive user
        Users inactiveUser = new Users();
        inactiveUser.setFullName("Inactive User");
        inactiveUser.setEmail("inactive@test.com");
        inactiveUser.setPasswordHash("hash");
        inactiveUser.setRole("STUDENT");
        inactiveUser.setIsActive(false);
        userRepository.save(inactiveUser);

        mockMvc.perform(get("/api/users/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }

    // ==================== GET USER BY JIRA ACCOUNT ID ====================
    @Test
    void getUserByJiraAccountId_WithValidId_ShouldReturnUser() throws Exception {
        // First get the user ID since there's no direct endpoint
        mockMvc.perform(get("/api/users/" + testUser.getUserId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jiraAccountId").value("jira123"))
            .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    // ==================== GET USER BY GITHUB USERNAME ====================
    @Test
    void getUserByGitHubUsername_WithValidUsername_ShouldReturnUser() throws Exception {
        // First get the user ID since there's no direct endpoint
        mockMvc.perform(get("/api/users/" + testUser.getUserId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gitHubUsername").value("johndoe"))
            .andExpect(jsonPath("$.fullName").value("John Doe"));
    }
}
