package com.example.demo.repository;

import com.example.demo.entity.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, Integer> {
    
    // Find by project ID
    List<ProjectMembers> findByProjectId(Integer projectId);
    
    // Find by user ID
    List<ProjectMembers> findByUserId(Integer userId);
    
    // Find by project and user (unique combination)
    Optional<ProjectMembers> findByProjectIdAndUserId(Integer projectId, Integer userId);
    
    // Find by role
    List<ProjectMembers> findByRole(String role);
    
    // Find active members in a project
    List<ProjectMembers> findByProjectIdAndIsActive(Integer projectId, Boolean isActive);
    
    // Find team leader of a project
    @Query("SELECT pm FROM ProjectMembers pm WHERE pm.projectId = :projectId AND pm.role = 'LEADER' AND pm.isActive = true")
    Optional<ProjectMembers> findTeamLeader(@Param("projectId") Integer projectId);
    
    // Find all projects for a user
    @Query("SELECT pm FROM ProjectMembers pm WHERE pm.userId = :userId AND pm.isActive = true")
    List<ProjectMembers> findActiveProjectsByUserId(@Param("userId") Integer userId);
    
    // Count members in a project
    long countByProjectIdAndIsActive(Integer projectId, Boolean isActive);
    
    // Check if user is in project
    boolean existsByProjectIdAndUserId(Integer projectId, Integer userId);
}
