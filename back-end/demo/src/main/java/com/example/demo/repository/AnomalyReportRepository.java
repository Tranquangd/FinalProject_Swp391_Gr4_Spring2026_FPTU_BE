package com.example.demo.repository;

import com.example.demo.entity.AnomalyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnomalyReportRepository extends JpaRepository<AnomalyReports, Integer> {
    
    // Find by project ID
    List<AnomalyReports> findByProjectId(Integer projectId);
    
    // Find by related user ID
    List<AnomalyReports> findByRelatedUserId(Integer relatedUserId);
    
    // Find by severity
    List<AnomalyReports> findBySeverity(String severity);
    
    // Find by issue type
    List<AnomalyReports> findByIssueType(String issueType);
    
    // Find by project and severity
    List<AnomalyReports> findByProjectIdAndSeverity(Integer projectId, String severity);
    
    // Find recent anomalies in project
    @Query("SELECT ar FROM AnomalyReports ar WHERE ar.projectId = :projectId ORDER BY ar.detectedDate DESC")
    List<AnomalyReports> findRecentByProject(@Param("projectId") Integer projectId);
    
    // Find anomalies detected after a specific date
    @Query("SELECT ar FROM AnomalyReports ar WHERE ar.detectedDate >= :since ORDER BY ar.detectedDate DESC")
    List<AnomalyReports> findDetectedSince(@Param("since") LocalDateTime since);
    
    // Find high severity anomalies
    @Query("SELECT ar FROM AnomalyReports ar WHERE ar.severity IN ('High', 'Critical') ORDER BY ar.detectedDate DESC")
    List<AnomalyReports> findHighSeverityAnomalies();
    
    // Get anomaly count by severity for a project
    @Query("SELECT ar.severity, COUNT(ar) FROM AnomalyReports ar WHERE ar.projectId = :projectId GROUP BY ar.severity")
    List<Object[]> getAnomalyCountBySeverity(@Param("projectId") Integer projectId);
    
    // Get anomalies by user in project
    @Query("SELECT ar FROM AnomalyReports ar WHERE ar.projectId = :projectId AND ar.relatedUserId = :userId ORDER BY ar.detectedDate DESC")
    List<AnomalyReports> findByProjectAndUser(@Param("projectId") Integer projectId, @Param("userId") Integer userId);
    
    // Count anomalies by project
    long countByProjectId(Integer projectId);
    
    // Count anomalies by user
    long countByRelatedUserId(Integer relatedUserId);
}
