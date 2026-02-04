package com.example.demo.controller;

import com.example.demo.entity.Courses;
import com.example.demo.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CoursesController {
    
    @Autowired
    private CoursesService coursesService;
    
    // GET /api/courses - Get all courses
    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        try {
            List<Courses> courses = coursesService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get courses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/courses/{id} - Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        try {
            Optional<Courses> course = coursesService.getCourseById(id);
            if (course.isPresent()) {
                return ResponseEntity.ok(course.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Course not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/courses/code/{courseCode} - Get course by code
    @GetMapping("/code/{courseCode}")
    public ResponseEntity<?> getCourseByCode(@PathVariable String courseCode) {
        try {
            Optional<Courses> course = coursesService.getCourseByCode(courseCode);
            if (course.isPresent()) {
                return ResponseEntity.ok(course.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Course not found with code: " + courseCode);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/courses/semester/{semesterId} - Get courses by semester ID
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<?> getCoursesBySemesterId(@PathVariable Integer semesterId) {
        try {
            List<Courses> courses = coursesService.getCoursesBySemesterId(semesterId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get courses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/courses/lecturer/{lecturerId} - Get courses by lecturer ID
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<?> getCoursesByLecturerId(@PathVariable Integer lecturerId) {
        try {
            List<Courses> courses = coursesService.getCoursesByLecturerId(lecturerId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get courses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/courses/search?name={name} - Search courses by name
    @GetMapping("/search")
    public ResponseEntity<?> searchCourses(@RequestParam String name) {
        try {
            List<Courses> courses = coursesService.searchCoursesByName(name);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to search courses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // POST /api/courses - Create new course
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Courses course) {
        try {
            Courses createdCourse = coursesService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // PUT /api/courses/{id} - Update course
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id, @RequestBody Courses courseDetails) {
        try {
            Courses updatedCourse = coursesService.updateCourse(id, courseDetails);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // DELETE /api/courses/{id} - Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        try {
            coursesService.deleteCourse(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Course deleted successfully");
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
