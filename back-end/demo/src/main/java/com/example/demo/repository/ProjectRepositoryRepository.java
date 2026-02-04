package com.example.demo.repository;

import com.example.demo.entity.ProjectRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepositoryRepository extends JpaRepository<ProjectRepositories, Integer> {
    
    // Find by project ID
    List<ProjectRepositories> findByProjectId(Integer projectId);
    
    // Find by repository owner
    List<ProjectRepositories> findByRepoOwner(String repoOwner);
    
    // Find by repository name
    List<ProjectRepositories> findByRepoName(String repoName);
    
    // Find by owner and name
    Optional<ProjectRepositories> findByRepoOwnerAndRepoName(String repoOwner, String repoName);
    
    // Find by repository URL
    Optional<ProjectRepositories> findByRepoUrl(String repoUrl);
    
    // Count repositories by project
    long countByProjectId(Integer projectId);
    
    // Check if repository exists by URL
    boolean existsByRepoUrl(String repoUrl);
    
    // Get all repositories with commit count
    @Query("SELECT pr, COUNT(gc) FROM ProjectRepositories pr " +
           "LEFT JOIN GithubCommits gc ON pr.repoId = gc.repoId " +
           "WHERE pr.projectId = :projectId GROUP BY pr")
    List<Object[]> findRepositoriesWithCommitCount(@Param("projectId") Integer projectId);
}
