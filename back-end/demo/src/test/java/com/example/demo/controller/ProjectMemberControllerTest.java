package com.example.demo.controller;

import com.example.demo.dto.request.AddProjectMemberRequest;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
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
class ProjectMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    private Projects testProject;
    private Users testLeader;
    private Users testMember;
    private ProjectMembers testProjectMember;
    private Courses testCourse;
    private Semesters testSemester;
    private AddProjectMemberRequest addMemberRequest;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        projectMemberRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();
        courseRepository.deleteAll();
        semesterRepository.deleteAll();

        // Create test semester
        testSemester = new Semesters();
        testSemester.setSemesterName("Fall 2026");
        testSemester.setStartDate(LocalDate.of(2026, 9, 1));
        testSemester.setEndDate(LocalDate.of(2026, 12, 31));
        testSemester = semesterRepository.save(testSemester);

        // Create test lecturer
        Users lecturer = new Users();
        lecturer.setFullName("Dr. Smith");
        lecturer.setEmail("smith@university.edu");
        lecturer.setPasswordHash("hashedpass");
        lecturer.setRole("LECTURER");
        lecturer.setIsActive(true);
        lecturer = userRepository.save(lecturer);

        // Create test course
        testCourse = new Courses();
        testCourse.setSemesterId(testSemester.getSemesterId());
        testCourse.setCourseCode("SWP391");
        testCourse.setCourseName("Software Project");
        testCourse.setLecturerId(lecturer.getUserId());
        testCourse = courseRepository.save(testCourse);

        // Create test project
        testProject = new Projects();
        testProject.setCourseId(testCourse.getCourseId());
        testProject.setGroupName("Group1");
        testProject.setProjectName("E-Commerce Platform");
        testProject.setProjectStatus("IN_PROGRESS");
        testProject = projectRepository.save(testProject);

        // Create test users
        testLeader = new Users();
        testLeader.setFullName("John Leader");
        testLeader.setEmail("leader@test.com");
        testLeader.setPasswordHash("hashedpass");
        testLeader.setRole("STUDENT");
        testLeader.setIsActive(true);
        testLeader = userRepository.save(testLeader);

        testMember = new Users();
        testMember.setFullName("Jane Member");
        testMember.setEmail("member@test.com");
        testMember.setPasswordHash("hashedpass");
        testMember.setRole("STUDENT");
        testMember.setIsActive(true);
        testMember = userRepository.save(testMember);

        // Create test project member
        testProjectMember = new ProjectMembers();
        testProjectMember.setProjectId(testProject.getProjectId());
        testProjectMember.setUserId(testLeader.getUserId());
        testProjectMember.setRole("LEADER");
        testProjectMember.setIsActive(true);
        testProjectMember = projectMemberRepository.save(testProjectMember);

        // Setup add member request
        addMemberRequest = new AddProjectMemberRequest();
        addMemberRequest.setUserId(testMember.getUserId());
        addMemberRequest.setRole("MEMBER");
    }

    @AfterEach
    void tearDown() {
        projectMemberRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();
        courseRepository.deleteAll();
        semesterRepository.deleteAll();
    }

    // ==================== GET PROJECT MEMBERS ====================
    @Test
    void getProjectMembers_ShouldReturnMemberList() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].fullName").value("John Leader"))
            .andExpect(jsonPath("$.data[0].role").value("LEADER"));
    }

    // ==================== GET ACTIVE MEMBERS ====================
    @Test
    void getActiveMembers_ShouldReturnOnlyActiveMembers() throws Exception {
        // Add inactive member
        Users inactiveMember = new Users();
        inactiveMember.setFullName("Inactive User");
        inactiveMember.setEmail("inactive@test.com");
        inactiveMember.setPasswordHash("hashedpass");
        inactiveMember.setRole("STUDENT");
        inactiveMember.setIsActive(true);
        inactiveMember = userRepository.save(inactiveMember);

        ProjectMembers inactivePM = new ProjectMembers();
        inactivePM.setProjectId(testProject.getProjectId());
        inactivePM.setUserId(inactiveMember.getUserId());
        inactivePM.setRole("MEMBER");
        inactivePM.setIsActive(false);
        projectMemberRepository.save(inactivePM);

        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].fullName").value("John Leader"));
    }

    // ==================== GET TEAM LEADER ====================
    @Test
    void getTeamLeader_ShouldReturnLeader() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members/leader"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.fullName").value("John Leader"))
            .andExpect(jsonPath("$.data.role").value("LEADER"));
    }

    // ==================== ADD MEMBER ====================
    @Test
    void addMember_WithValidData_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/projects/" + testProject.getProjectId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addMemberRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.fullName").value("Jane Member"))
            .andExpect(jsonPath("$.data.role").value("MEMBER"));
    }

    @Test
    void addMember_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        addMemberRequest.setUserId(null); // Invalid - userId is required

        mockMvc.perform(post("/api/projects/" + testProject.getProjectId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addMemberRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addMember_WithDuplicateUser_ShouldReturnBadRequest() throws Exception {
        addMemberRequest.setUserId(testLeader.getUserId()); // Already exists

        mockMvc.perform(post("/api/projects/" + testProject.getProjectId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addMemberRequest)))
            .andExpect(status().isConflict());
    }

    // ==================== UPDATE MEMBER ROLE ====================
    @Test
    void updateMemberRole_WithValidData_ShouldReturnUpdatedMember() throws Exception {
        // First add the member
        mockMvc.perform(post("/api/projects/" + testProject.getProjectId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addMemberRequest)))
            .andExpect(status().isCreated());

        // Update role (change to MEMBER to avoid multiple leader constraint)
        mockMvc.perform(put("/api/projects/" + testProject.getProjectId() + "/members/" + testMember.getUserId() + "/role?role=MEMBER"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.role").value("MEMBER"));
    }

    // ==================== DEACTIVATE MEMBER ====================
    @Test
    void deactivateMember_WithValidId_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(patch("/api/projects/" + testProject.getProjectId() + "/members/" + testLeader.getUserId() + "/deactivate"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    // ==================== ACTIVATE MEMBER ====================
    @Test
    void activateMember_WithValidId_ShouldReturnSuccess() throws Exception {
        // First deactivate
        mockMvc.perform(patch("/api/projects/" + testProject.getProjectId() + "/members/" + testLeader.getUserId() + "/deactivate"))
            .andExpect(status().isOk());

        // Then activate
        mockMvc.perform(patch("/api/projects/" + testProject.getProjectId() + "/members/" + testLeader.getUserId() + "/activate"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    // ==================== REMOVE MEMBER ====================
    @Test
    void removeMember_WithValidId_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/api/projects/" + testProject.getProjectId() + "/members/" + testLeader.getUserId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));

        // Verify removal
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(0)));
    }

    // ==================== IS TEAM LEADER ====================
    @Test
    void isTeamLeader_ForLeader_ShouldReturnTrue() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members/" + testLeader.getUserId() + "/is-leader"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    void isTeamLeader_ForNonLeader_ShouldReturnFalse() throws Exception {
        // Add member first
        mockMvc.perform(post("/api/projects/" + testProject.getProjectId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addMemberRequest)))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/api/projects/" + testProject.getProjectId() + "/members/" + testMember.getUserId() + "/is-leader"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").value(false));
    }
}
