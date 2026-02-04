package com.example.demo.service;

import com.example.demo.dto.request.CreateProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.entity.Courses;
import com.example.demo.entity.Projects;
import com.example.demo.entity.Semesters;
import com.example.demo.entity.Users;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.SemesterRepository;
import com.example.demo.repository.UserRepository;
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
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

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

    // ==================== CREATE PROJECT ====================
    @Test
    void createProject_WithValidData_ShouldReturnProjectResponse() {
        ProjectResponse result = projectService.createProject(createRequest);

        assertNotNull(result);
        assertEquals("Group2", result.getGroupName());
        assertEquals("Social Media App", result.getProjectName());
        assertEquals("A social networking application", result.getDescription());
        assertEquals("IN_PROGRESS", result.getProjectStatus());
    }

    @Test
    void createProject_WithDuplicateGroupName_ShouldThrowException() {
        createRequest.setGroupName("Group1"); // Already exists

        assertThrows(DuplicateResourceException.class, 
            () -> projectService.createProject(createRequest));
    }

    @Test
    void createProject_WithInvalidCourseId_ShouldThrowException() {
        createRequest.setCourseId(99999);

        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.createProject(createRequest));
    }

    // ==================== GET ALL PROJECTS ====================
    @Test
    void getAllProjects_ShouldReturnAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Group1", projects.get(0).getGroupName());
    }

    // ==================== GET PROJECT BY ID ====================
    @Test
    void getProjectById_WithValidId_ShouldReturnProjectResponse() {
        ProjectResponse result = projectService.getProjectById(testProject.getProjectId());

        assertNotNull(result);
        assertEquals(testProject.getProjectId(), result.getProjectId());
        assertEquals("Group1", result.getGroupName());
        assertEquals("E-Commerce Platform", result.getProjectName());
    }

    @Test
    void getProjectById_WithInvalidId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.getProjectById(99999));
    }

    // ==================== GET PROJECTS BY COURSE ====================
    @Test
    void getProjectsByCourse_ShouldReturnProjects() {
        List<ProjectResponse> result = projectService.getProjectsByCourse(testCourse.getCourseId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCourse.getCourseId(), result.get(0).getCourseId());
    }

    @Test
    void getProjectsByCourse_WithInvalidCourseId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.getProjectsByCourse(99999));
    }

    // ==================== GET PROJECTS BY STATUS ====================
    @Test
    void getProjectsByStatus_ShouldReturnMatchingProjects() {
        List<ProjectResponse> result = projectService.getProjectsByStatus("IN_PROGRESS");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IN_PROGRESS", result.get(0).getProjectStatus());
    }

    @Test
    void getProjectsByStatus_WithNoMatches_ShouldReturnEmptyList() {
        List<ProjectResponse> result = projectService.getProjectsByStatus("COMPLETED");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ==================== SEARCH PROJECTS ====================
    @Test
    void searchProjectsByName_ShouldReturnMatchingProjects() {
        List<ProjectResponse> result = projectService.searchProjectsByName("Commerce");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getProjectName().contains("Commerce"));
    }

    @Test
    void searchProjectsByName_WithNoMatches_ShouldReturnEmptyList() {
        List<ProjectResponse> result = projectService.searchProjectsByName("NonExistent");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ==================== UPDATE PROJECT ====================
    @Test
    void updateProject_WithValidData_ShouldReturnUpdatedProject() {
        createRequest.setGroupName("UpdatedGroup");
        createRequest.setProjectName("Updated Project");

        ProjectResponse result = projectService.updateProject(testProject.getProjectId(), createRequest);

        assertNotNull(result);
        assertEquals("UpdatedGroup", result.getGroupName());
        assertEquals("Updated Project", result.getProjectName());
    }

    @Test
    void updateProject_WithInvalidId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.updateProject(99999, createRequest));
    }

    // ==================== UPDATE PROJECT STATUS ====================
    @Test
    void updateProjectStatus_WithValidData_ShouldReturnUpdatedProject() {
        ProjectResponse result = projectService.updateProjectStatus(testProject.getProjectId(), "COMPLETED");

        assertNotNull(result);
        assertEquals("COMPLETED", result.getProjectStatus());
    }

    @Test
    void updateProjectStatus_WithInvalidId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.updateProjectStatus(99999, "COMPLETED"));
    }

    // ==================== DELETE PROJECT ====================
    @Test
    void deleteProject_WithValidId_ShouldDeleteProject() {
        projectService.deleteProject(testProject.getProjectId());

        assertFalse(projectRepository.existsById(testProject.getProjectId()));
    }

    @Test
    void deleteProject_WithInvalidId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, 
            () -> projectService.deleteProject(99999));
    }
}
