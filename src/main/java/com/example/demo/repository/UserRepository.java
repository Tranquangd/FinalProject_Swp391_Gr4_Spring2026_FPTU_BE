package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    
    // Find by email
    Optional<Users> findByEmail(String email);
    
    // Find by Jira Account ID
    Optional<Users> findByJiraAccountId(String jiraAccountId);
    
    // Find by GitHub username
    Optional<Users> findByGitHubUsername(String gitHubUsername);
    
    // Find by role
    List<Users> findByRole(String role);
    
    // Find active users
    List<Users> findByIsActiveTrue();
    
    // Find inactive users
    List<Users> findByIsActiveFalse();
    
    // Check if email exists
    boolean existsByEmail(String email);
    
    // Search by name (case insensitive)
    @Query("SELECT u FROM Users u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Users> searchByFullName(@Param("name") String name);
    
    // Find by role and active status
    List<Users> findByRoleAndIsActive(String role, Boolean isActive);
}
