package com.example.demo.service;

import com.example.demo.entity.Courses;
import com.example.demo.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoursesService {
    
    @Autowired
    private CoursesRepository coursesRepository;
    
    // Get all courses
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }
    
    // Get course by ID
    public Optional<Courses> getCourseById(Integer id) {
        return coursesRepository.findById(id);
    }
    
    // Get courses by semester ID
    public List<Courses> getCoursesBySemesterId(Integer semesterId) {
        return coursesRepository.findBySemesterid(semesterId);
    }
    
    // Get courses by lecturer ID
    public List<Courses> getCoursesByLecturerId(Integer lecturerId) {
        return coursesRepository.findByLecturerid(lecturerId);
    }
    
    // Get course by course code
    public Optional<Courses> getCourseByCode(String courseCode) {
        return coursesRepository.findByCoursecode(courseCode);
    }
    
    // Search courses by name
    public List<Courses> searchCoursesByName(String name) {
        return coursesRepository.searchByName(name);
    }
    
    // Create new course
    public Courses createCourse(Courses course) {
        // Check if course code already exists
        if (course.getCoursecode() != null && coursesRepository.findByCoursecode(course.getCoursecode()).isPresent()) {
            throw new RuntimeException("Course code already exists: " + course.getCoursecode());
        }
        
        return coursesRepository.save(course);
    }
    
    // Update course
    public Courses updateCourse(Integer id, Courses courseDetails) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Update fields
        if (courseDetails.getSemesterid() != null) {
            course.setSemesterid(courseDetails.getSemesterid());
        }
        if (courseDetails.getCoursecode() != null && !courseDetails.getCoursecode().equals(course.getCoursecode())) {
            // Check if new course code already exists
            if (coursesRepository.findByCoursecode(courseDetails.getCoursecode()).isPresent()) {
                throw new RuntimeException("Course code already exists: " + courseDetails.getCoursecode());
            }
            course.setCoursecode(courseDetails.getCoursecode());
        }
        if (courseDetails.getCoursename() != null) {
            course.setCoursename(courseDetails.getCoursename());
        }
        if (courseDetails.getLecturerid() != null) {
            course.setLecturerid(courseDetails.getLecturerid());
        }
        
        return coursesRepository.save(course);
    }
    
    // Delete course
    public void deleteCourse(Integer id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        coursesRepository.delete(course);
    }
}
