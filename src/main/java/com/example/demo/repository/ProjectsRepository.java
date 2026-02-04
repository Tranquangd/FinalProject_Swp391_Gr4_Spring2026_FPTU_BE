package com.example.demo.repository;

import com.example.demo.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
    
    // Find projects by course ID
    List<Projects> findByCourseid(Integer courseId);
    
    // Find projects by status
    List<Projects> findByProjectstatus(String status);
    
    // Search projects by name
    @Query("SELECT p FROM Projects p WHERE LOWER(p.projectname) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.groupname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Projects> searchByName(@Param("name") String name);
    
    // Find projects by course and status
    List<Projects> findByCourseidAndProjectstatus(Integer courseId, String status);
}
