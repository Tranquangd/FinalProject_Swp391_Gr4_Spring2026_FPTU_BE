package com.example.demo.repository;

import com.example.demo.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    
    // Find courses by semester ID
    List<Courses> findBySemesterid(Integer semesterId);
    
    // Find courses by lecturer ID
    List<Courses> findByLecturerid(Integer lecturerId);
    
    // Find course by course code
    Optional<Courses> findByCoursecode(String courseCode);
    
    // Search courses by name
    @Query("SELECT c FROM Courses c WHERE LOWER(c.coursename) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.coursecode) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Courses> searchByName(@Param("name") String name);
    
    // Find courses by semester and lecturer
    List<Courses> findBySemesteridAndLecturerid(Integer semesterId, Integer lecturerId);
}
