package com.example.demo.repository;

import com.example.demo.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer> {
    
    // Find by semester ID
    List<Courses> findBySemesterId(Integer semesterId);
    
    // Find by course code
    Optional<Courses> findByCourseCode(String courseCode);
    
    // Find by lecturer ID
    List<Courses> findByLecturerId(Integer lecturerId);
    
    // Find by semester and lecturer
    List<Courses> findBySemesterIdAndLecturerId(Integer semesterId, Integer lecturerId);
    
    // Search by course name
    @Query("SELECT c FROM Courses c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Courses> searchByCourseName(@Param("name") String name);
    
    // Check if course code exists
    boolean existsByCourseCode(String courseCode);
    
    // Get courses with project count
    @Query("SELECT c FROM Courses c LEFT JOIN Projects p ON c.courseId = p.courseId GROUP BY c")
    List<Courses> findAllWithProjectCount();
}
