package com.example.demo.repository;

import com.example.demo.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Integer> {
    
    // Find by course ID
    List<Projects> findByCourseId(Integer courseId);
    
    // Find by group name
    Optional<Projects> findByGroupName(String groupName);
    
    // Find by project status
    List<Projects> findByProjectStatus(String projectStatus);
    
    // Find by course and status
    List<Projects> findByCourseIdAndProjectStatus(Integer courseId, String projectStatus);
    
    // Search by project name (case insensitive)
    @Query("SELECT p FROM Projects p WHERE LOWER(p.projectName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Projects> searchByProjectName(@Param("name") String name);
    
    // Find projects by lecturer ID (through course)
    @Query("SELECT p FROM Projects p JOIN p.course c WHERE c.lecturerId = :lecturerId")
    List<Projects> findByLecturerId(@Param("lecturerId") Integer lecturerId);
    
    // Check if group name exists
    boolean existsByGroupName(String groupName);
}
