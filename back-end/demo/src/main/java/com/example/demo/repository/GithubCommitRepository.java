package com.example.demo.repository;

import com.example.demo.entity.GithubCommits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GithubCommitRepository extends JpaRepository<GithubCommits, Integer> {
    
    // Find by repository ID
    List<GithubCommits> findByRepoId(Integer repoId);
    
    // Find by SHA
    Optional<GithubCommits> findBySha(String sha);
    
    // Find by committer email
    List<GithubCommits> findByCommitterEmail(String committerEmail);
    
    // Find by committer name
    List<GithubCommits> findByCommitterName(String committerName);
    
    // Find by repository and date range
    @Query("SELECT gc FROM GithubCommits gc WHERE gc.repoId = :repoId AND gc.commitDate BETWEEN :startDate AND :endDate ORDER BY gc.commitDate DESC")
    List<GithubCommits> findByRepoAndDateRange(@Param("repoId") Integer repoId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Find by committer email and date range
    @Query("SELECT gc FROM GithubCommits gc WHERE gc.committerEmail = :email AND gc.commitDate BETWEEN :startDate AND :endDate ORDER BY gc.commitDate DESC")
    List<GithubCommits> findByEmailAndDateRange(@Param("email") String email, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Get commit statistics by user
    @Query("SELECT gc.committerEmail, COUNT(gc), SUM(gc.additions), SUM(gc.deletions) FROM GithubCommits gc WHERE gc.repoId = :repoId GROUP BY gc.committerEmail")
    List<Object[]> getCommitStatsByRepo(@Param("repoId") Integer repoId);
    
    // Get commit frequency by user in project
    @Query("SELECT gc.committerEmail, COUNT(gc), SUM(gc.additions), SUM(gc.deletions) FROM GithubCommits gc " +
           "JOIN gc.repository pr WHERE pr.projectId = :projectId GROUP BY gc.committerEmail")
    List<Object[]> getCommitStatsByProject(@Param("projectId") Integer projectId);
    
    // Get recent commits in repository
    @Query("SELECT gc FROM GithubCommits gc WHERE gc.repoId = :repoId ORDER BY gc.commitDate DESC")
    List<GithubCommits> findRecentCommitsByRepo(@Param("repoId") Integer repoId);
    
    // Count commits by user in repository
    long countByRepoIdAndCommitterEmail(Integer repoId, String committerEmail);
    
    // Find commits after specific date
    @Query("SELECT gc FROM GithubCommits gc WHERE gc.commitDate >= :since ORDER BY gc.commitDate DESC")
    List<GithubCommits> findCommitsSince(@Param("since") LocalDateTime since);
    
    // Check if SHA exists
    boolean existsBySha(String sha);
    
    // Get daily commit count for a project
    @Query("SELECT CAST(gc.commitDate AS date), COUNT(gc) FROM GithubCommits gc " +
           "JOIN gc.repository pr WHERE pr.projectId = :projectId " +
           "GROUP BY CAST(gc.commitDate AS date) ORDER BY CAST(gc.commitDate AS date) DESC")
    List<Object[]> getDailyCommitCountByProject(@Param("projectId") Integer projectId);
}
