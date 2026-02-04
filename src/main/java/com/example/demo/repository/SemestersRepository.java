package com.example.demo.repository;

import com.example.demo.entity.Semesters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SemestersRepository extends JpaRepository<Semesters, Integer> {
    
    // Find semester by name
    Optional<Semesters> findBySemestername(String semesterName);
    
    // Find semesters by date range
    @Query("SELECT s FROM Semesters s WHERE s.startdate <= :date AND s.enddate >= :date")
    List<Semesters> findActiveSemestersByDate(@Param("date") LocalDate date);
    
    // Search semesters by name
    @Query("SELECT s FROM Semesters s WHERE LOWER(s.semestername) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Semesters> searchByName(@Param("name") String name);
}
