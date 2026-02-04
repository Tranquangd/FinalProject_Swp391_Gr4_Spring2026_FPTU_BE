package com.example.demo.repository;

import com.example.demo.entity.SrsDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SrsDocumentRepository extends JpaRepository<SrsDocuments, Integer> {
    
    // Find by project ID
    List<SrsDocuments> findByProjectId(Integer projectId);
    
    // Find by exported by user ID
    List<SrsDocuments> findByExportedBy(Integer exportedBy);
    
    // Find by project and version
    Optional<SrsDocuments> findByProjectIdAndVersionNumber(Integer projectId, String versionNumber);
    
    // Find latest version for a project
    @Query("SELECT s FROM SrsDocuments s WHERE s.projectId = :projectId ORDER BY s.exportedDate DESC")
    List<SrsDocuments> findLatestByProject(@Param("projectId") Integer projectId);
    
    // Get latest SRS document
    @Query("SELECT s FROM SrsDocuments s WHERE s.projectId = :projectId ORDER BY s.exportedDate DESC LIMIT 1")
    Optional<SrsDocuments> findLatestSrsDocument(@Param("projectId") Integer projectId);
    
    // Find documents exported in date range
    @Query("SELECT s FROM SrsDocuments s WHERE s.exportedDate BETWEEN :startDate AND :endDate ORDER BY s.exportedDate DESC")
    List<SrsDocuments> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Find documents by project ordered by version
    @Query("SELECT s FROM SrsDocuments s WHERE s.projectId = :projectId ORDER BY s.versionNumber DESC")
    List<SrsDocuments> findByProjectOrderByVersion(@Param("projectId") Integer projectId);
    
    // Count documents by project
    long countByProjectId(Integer projectId);
    
    // Check if version exists for project
    boolean existsByProjectIdAndVersionNumber(Integer projectId, String versionNumber);
}
