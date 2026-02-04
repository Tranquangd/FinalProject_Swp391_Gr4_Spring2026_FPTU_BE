package com.example.demo.controller;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.entity.Courses;
import com.example.demo.entity.Projects;
import com.example.demo.entity.Semesters;
import com.example.demo.entity.Users;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.SemesterRepository;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private UserRepository userRepository;

    private Projects testProject;
    private Courses testCourse;
    private Semesters testSemester;
    private Users testLecturer;
    private CreateProjectRequest createRequest;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        projectRepository.deleteAll();
        courseRepository.deleteAll();
        semesterRepository.deleteAll();
        userRepository.deleteAll();

        // Create test semester
        testSemester = new Semesters();
        testSemester.setSemesterName("Fall 2026");
        testSemester.setStartDate(LocalDate.of(2026, 9, 1));
        testSemester.setEndDate(LocalDate.of(2026, 12, 31));
        testSemester = semesterRepository.save(testSemester);

        // Create test lecturer
        testLecturer = new Users();
        testLecturer.setFullName("Dr. Smith");
        testLecturer.setEmail("smith@university.edu");
        testLecturer.setPasswordHash("hashedpass");
        testLecturer.setRole("LECTURER");
        testLecturer.setIsActive(true);
        testLecturer = userRepository.save(testLecturer);

        // Create test course
        testCourse = new Courses();
        testCourse.setSemesterId(testSemester.getSemesterId());
        testCourse.setCourseCode("SWP391");
        testCourse.setCourseName("Software Project");
        testCourse.setLecturerId(testLecturer.getUserId());
        testCourse = courseRepository.save(testCourse);

        // Create test project
        testProject = new Projects();
        testProject.setCourseId(testCourse.getCourseId());
        testProject.setGroupName("Group1");
        testProject.setProjectName("E-Commerce Platform");
        testProject.setDescription("An online shopping platform");
        testProject.setProjectStatus("IN_PROGRESS");
        testProject = projectRepository.save(testProject);

        // Setup create request
        createRequest = new CreateProjectRequest();
        createRequest.setCourseId(testCourse.getCourseId());
        createRequest.setGroupName("Group2");
        createRequest.setProjectName("Social Media App");
        createRequest.setDescription("A social networking application");
        createRequest.setProjectStatus("IN_PROGRESS");
    }

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
        courseRepository.deleteAll();
        semesterRepository.deleteAll();
        userRepository.deleteAll();
    }

    // ==================== GET ALL PROJECTS ====================
    @Test
    void getAllProjects_ShouldReturnProjectList() throws Exception {
        mockMvc.perform(get("/api/projects"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].groupName").value("Group1"))
            .andExpect(jsonPath("$.data[0].projectName").value("E-Commerce Platform"));
    }

    // ==================== GET PROJECT BY ID ====================
    @Test
    void getProjectById_WithValidId_ShouldReturnProject() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.groupName").value("Group1"))
            .andExpect(jsonPath("$.data.projectName").value("E-Commerce Platform"));
    }

    @Test
    void getProjectById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/projects/99999"))
            .andExpect(status().isNotFound());
    }

    // ==================== GET PROJECTS BY COURSE ====================
    @Test
    void getProjectsByCourse_ShouldReturnProjects() throws Exception {
        mockMvc.perform(get("/api/projects/course/" + testCourse.getCourseId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].courseId").value(testCourse.getCourseId()));
    }

    // ==================== GET PROJECTS BY STATUS ====================
    @Test
    void getProjectsByStatus_ShouldReturnMatchingProjects() throws Exception {
        mockMvc.perform(get("/api/projects/status/IN_PROGRESS"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].projectStatus").value("IN_PROGRESS"));
    }

    // ==================== SEARCH PROJECTS ====================
    @Test
    void searchProjects_ShouldReturnMatchingProjects() throws Exception {
        mockMvc.perform(get("/api/projects/search?name=Commerce"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].projectName").value("E-Commerce Platform"));
    }

    // ==================== CREATE PROJECT ====================
    @Test
    void createProject_WithValidData_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.groupName").value("Group2"))
            .andExpect(jsonPath("$.data.projectName").value("Social Media App"));
    }

    @Test
    void createProject_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        createRequest.setGroupName(null); // Invalid - group name is required

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createProject_WithDuplicateGroupName_ShouldReturnBadRequest() throws Exception {
        createRequest.setGroupName("Group1"); // Already exists

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isConflict());
    }

    // ==================== UPDATE PROJECT ====================
    @Test
    void updateProject_WithValidData_ShouldReturnUpdatedProject() throws Exception {
        createRequest.setGroupName("UpdatedGroup");
        createRequest.setProjectName("Updated Project Name");

        mockMvc.perform(put("/api/projects/" + testProject.getProjectId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.groupName").value("UpdatedGroup"))
            .andExpect(jsonPath("$.data.projectName").value("Updated Project Name"));
    }

    @Test
    void updateProject_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/projects/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isNotFound());
    }

    // ==================== UPDATE PROJECT STATUS ====================
    @Test
    void updateProjectStatus_WithValidData_ShouldReturnUpdatedProject() throws Exception {
        mockMvc.perform(patch("/api/projects/" + testProject.getProjectId() + "/status?status=COMPLETED"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.projectStatus").value("COMPLETED"));
    }

    // ==================== DELETE PROJECT ====================
    @Test
    void deleteProject_WithValidId_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/api/projects/" + testProject.getProjectId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));

        // Verify deletion
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId()))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteProject_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/projects/99999"))
            .andExpect(status().isNotFound());
    }
}
