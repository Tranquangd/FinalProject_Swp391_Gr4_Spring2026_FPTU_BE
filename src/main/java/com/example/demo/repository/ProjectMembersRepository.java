package com.example.demo.repository;

import com.example.demo.entity.Projectmembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMembersRepository extends JpaRepository<Projectmembers, Integer> {
    
    // Find all members by project ID
    @Query("SELECT pm FROM Projectmembers pm WHERE pm.projectid = :projectId")
    List<Projectmembers> findByProjectid(@Param("projectId") Integer projectId);
    
    // Find all projects by user ID
    @Query("SELECT pm FROM Projectmembers pm WHERE pm.userid = :userId")
    List<Projectmembers> findByUserid(@Param("userId") Integer userId);
    
    // Find specific project member
    @Query("SELECT pm FROM Projectmembers pm WHERE pm.projectid = :projectId AND pm.userid = :userId")
    Optional<Projectmembers> findByProjectidAndUserid(@Param("projectId") Integer projectId, @Param("userId") Integer userId);
    
    // Find active members by project ID
    @Query("SELECT pm FROM Projectmembers pm WHERE pm.projectid = :projectId AND pm.isactive = true")
    List<Projectmembers> findActiveMembersByProjectid(@Param("projectId") Integer projectId);
    
    // Find members by role
    @Query("SELECT pm FROM Projectmembers pm WHERE pm.projectid = :projectId AND pm.role = :role")
    List<Projectmembers> findByProjectidAndRole(@Param("projectId") Integer projectId, @Param("role") String role);
}
