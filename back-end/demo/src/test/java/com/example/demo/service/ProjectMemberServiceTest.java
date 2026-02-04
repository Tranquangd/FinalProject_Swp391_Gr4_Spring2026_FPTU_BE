package com.example.demo.service;

import com.example.demo.dto.request.AddProjectMemberRequest;
import com.example.demo.dto.response.ProjectMemberResponse;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectMemberServiceTest {

    @Autowired
    private ProjectMemberService projectMemberService;

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

    // ==================== ADD PROJECT MEMBER ====================
    @Test
    void addProjectMember_WithValidData_ShouldReturnMemberResponse() {
        ProjectMemberResponse result = projectMemberService.addProjectMember(testProject.getProjectId(), addMemberRequest);

        assertNotNull(result);
        assertEquals(testMember.getUserId(), result.getUserId());
        assertEquals("Jane Member", result.getFullName());
        assertEquals("MEMBER", result.getRole());
        assertTrue(result.getIsActive());
    }

    @Test
    void addProjectMember_WithDuplicateUser_ShouldThrowException() {
        addMemberRequest.setUserId(testLeader.getUserId()); // Already exists

        assertThrows(DuplicateResourceException.class, 
            () -> projectMemberService.addProjectMember(testProject.getProjectId(), addMemberRequest));
    }

    @Test
    void addProjectMember_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.addProjectMember(99999, addMemberRequest));
    }

    @Test
    void addProjectMember_WithInvalidUserId_ShouldThrowException() {
        addMemberRequest.setUserId(99999);

        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.addProjectMember(testProject.getProjectId(), addMemberRequest));
    }

    // ==================== GET PROJECT MEMBERS ====================
    @Test
    void getProjectMembers_ShouldReturnAllMembers() {
        List<ProjectMemberResponse> members = projectMemberService.getProjectMembers(testProject.getProjectId());

        assertNotNull(members);
        assertEquals(1, members.size());
        assertEquals("John Leader", members.get(0).getFullName());
        assertEquals("LEADER", members.get(0).getRole());
    }

    @Test
    void getProjectMembers_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.getProjectMembers(99999));
    }

    // ==================== GET ACTIVE MEMBERS ====================
    @Test
    void getActiveProjectMembers_ShouldReturnOnlyActiveMembers() {
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

        List<ProjectMemberResponse> members = projectMemberService.getActiveProjectMembers(testProject.getProjectId());

        assertNotNull(members);
        assertEquals(1, members.size());
        assertTrue(members.get(0).getIsActive());
    }

    // ==================== GET TEAM LEADER ====================
    @Test
    void getTeamLeader_ShouldReturnLeader() {
        ProjectMemberResponse leader = projectMemberService.getTeamLeader(testProject.getProjectId());

        assertNotNull(leader);
        assertEquals("John Leader", leader.getFullName());
        assertEquals("LEADER", leader.getRole());
    }

    @Test
    void getTeamLeader_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.getTeamLeader(99999));
    }

    // ==================== UPDATE MEMBER ROLE ====================
    @Test
    void updateMemberRole_WithValidData_ShouldReturnUpdatedMember() {
        // First add the member
        projectMemberService.addProjectMember(testProject.getProjectId(), addMemberRequest);

        // Update role to a different non-leader role (since we already have a leader)
        ProjectMemberResponse result = projectMemberService.updateMemberRole(
            testProject.getProjectId(), 
            testMember.getUserId(), 
            "MEMBER"
        );

        assertNotNull(result);
        assertEquals("MEMBER", result.getRole());
    }

    @Test
    void updateMemberRole_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.updateMemberRole(99999, testLeader.getUserId(), "MEMBER"));
    }

    // ==================== DEACTIVATE MEMBER ====================
    @Test
    void deactivateMember_WithValidId_ShouldDeactivateMember() {
        projectMemberService.deactivateMember(testProject.getProjectId(), testLeader.getUserId());

        // Verify deactivation
        List<ProjectMemberResponse> activeMembers = projectMemberService.getActiveProjectMembers(testProject.getProjectId());
        assertEquals(0, activeMembers.size());
    }

    @Test
    void deactivateMember_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.deactivateMember(99999, testLeader.getUserId()));
    }

    // ==================== ACTIVATE MEMBER ====================
    @Test
    void activateMember_WithValidId_ShouldActivateMember() {
        // First deactivate
        projectMemberService.deactivateMember(testProject.getProjectId(), testLeader.getUserId());

        // Then activate
        projectMemberService.activateMember(testProject.getProjectId(), testLeader.getUserId());

        // Verify activation
        List<ProjectMemberResponse> activeMembers = projectMemberService.getActiveProjectMembers(testProject.getProjectId());
        assertEquals(1, activeMembers.size());
    }

    // ==================== REMOVE MEMBER ====================
    @Test
    void removeMember_WithValidId_ShouldRemoveMember() {
        projectMemberService.removeMember(testProject.getProjectId(), testLeader.getUserId());

        // Verify removal
        List<ProjectMemberResponse> members = projectMemberService.getProjectMembers(testProject.getProjectId());
        assertEquals(0, members.size());
    }

    @Test
    void removeMember_WithInvalidProjectId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectMemberService.removeMember(99999, testLeader.getUserId()));
    }

    // ==================== IS TEAM LEADER ====================
    @Test
    void isTeamLeader_ForLeader_ShouldReturnTrue() {
        boolean result = projectMemberService.isTeamLeader(testProject.getProjectId(), testLeader.getUserId());

        assertTrue(result);
    }

    @Test
    void isTeamLeader_ForNonLeader_ShouldReturnFalse() {
        // Add member first
        projectMemberService.addProjectMember(testProject.getProjectId(), addMemberRequest);

        boolean result = projectMemberService.isTeamLeader(testProject.getProjectId(), testMember.getUserId());

        assertFalse(result);
    }
}
