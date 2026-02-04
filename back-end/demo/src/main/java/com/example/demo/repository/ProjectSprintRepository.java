package com.example.demo.repository;

import com.example.demo.entity.ProjectSprints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectSprintRepository extends JpaRepository<ProjectSprints, Integer> {
    
    // Find by project ID
    List<ProjectSprints> findByProjectId(Integer projectId);
    
    // Find by Jira sprint ID
    Optional<ProjectSprints> findByJiraSprintId(Integer jiraSprintId);
    
    // Find by sprint state
    List<ProjectSprints> findBySprintState(String sprintState);
    
    // Find by project and state
    List<ProjectSprints> findByProjectIdAndSprintState(Integer projectId, String sprintState);
    
    // Find active sprint in project
    @Query("SELECT ps FROM ProjectSprints ps WHERE ps.projectId = :projectId AND ps.sprintState = 'active' ORDER BY ps.startDate DESC")
    Optional<ProjectSprints> findActiveSprintByProject(@Param("projectId") Integer projectId);
    
    // Find current sprint (active or between start and end date)
    @Query("SELECT ps FROM ProjectSprints ps WHERE ps.projectId = :projectId AND " +
           "(ps.sprintState = 'active' OR (:currentDate BETWEEN ps.startDate AND ps.endDate)) " +
           "ORDER BY ps.startDate DESC")
    Optional<ProjectSprints> findCurrentSprint(@Param("projectId") Integer projectId, @Param("currentDate") LocalDateTime currentDate);
    
    // Find sprints ordered by start date
    @Query("SELECT ps FROM ProjectSprints ps WHERE ps.projectId = :projectId ORDER BY ps.startDate DESC")
    List<ProjectSprints> findByProjectOrderByStartDate(@Param("projectId") Integer projectId);
    
    // Find future sprints
    @Query("SELECT ps FROM ProjectSprints ps WHERE ps.projectId = :projectId AND ps.startDate > :currentDate ORDER BY ps.startDate ASC")
    List<ProjectSprints> findFutureSprints(@Param("projectId") Integer projectId, @Param("currentDate") LocalDateTime currentDate);
    
    // Find completed sprints
    @Query("SELECT ps FROM ProjectSprints ps WHERE ps.projectId = :projectId AND ps.sprintState = 'closed' ORDER BY ps.endDate DESC")
    List<ProjectSprints> findCompletedSprints(@Param("projectId") Integer projectId);
    
    // Count sprints by project
    long countByProjectId(Integer projectId);
    
    // Check if Jira sprint ID exists
    boolean existsByJiraSprintId(Integer jiraSprintId);
}
