package com.example.demo.repository;

import com.example.demo.entity.ProjectConfigs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectConfigRepository extends JpaRepository<ProjectConfigs, Integer> {
    
    // Find by project ID (should be unique)
    Optional<ProjectConfigs> findByProjectId(Integer projectId);
    
    // Find by Jira project key
    Optional<ProjectConfigs> findByJiraProjectKey(String jiraProjectKey);
    
    // Check if project has config
    boolean existsByProjectId(Integer projectId);
    
    // Check if Jira URL is already configured
    boolean existsByJiraUrl(String jiraUrl);
    
    // Verify if project has complete Jira configuration
    @Query("SELECT CASE WHEN (pc.jiraUrl IS NOT NULL AND pc.jiraProjectKey IS NOT NULL AND pc.jiraEmail IS NOT NULL AND pc.jiraApiToken IS NOT NULL) THEN true ELSE false END " +
           "FROM ProjectConfigs pc WHERE pc.projectId = :projectId")
    Boolean hasCompleteJiraConfig(@Param("projectId") Integer projectId);
    
    // Verify if project has GitHub token configured
    @Query("SELECT CASE WHEN pc.gitHubToken IS NOT NULL THEN true ELSE false END " +
           "FROM ProjectConfigs pc WHERE pc.projectId = :projectId")
    Boolean hasGithubToken(@Param("projectId") Integer projectId);
}
