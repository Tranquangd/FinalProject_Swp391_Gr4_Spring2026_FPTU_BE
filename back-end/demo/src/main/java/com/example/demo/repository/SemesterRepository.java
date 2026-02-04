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
public interface SemesterRepository extends JpaRepository<Semesters, Integer> {
    
    // Find by semester name
    Optional<Semesters> findBySemesterName(String semesterName);
    
    // Find current semester (today is between start and end date)
    @Query("SELECT s FROM Semesters s WHERE :currentDate BETWEEN s.startDate AND s.endDate")
    Optional<Semesters> findCurrentSemester(@Param("currentDate") LocalDate currentDate);
    
    // Find upcoming semesters
    @Query("SELECT s FROM Semesters s WHERE s.startDate > :currentDate ORDER BY s.startDate ASC")
    List<Semesters> findUpcomingSemesters(@Param("currentDate") LocalDate currentDate);
    
    // Find past semesters
    @Query("SELECT s FROM Semesters s WHERE s.endDate < :currentDate ORDER BY s.endDate DESC")
    List<Semesters> findPastSemesters(@Param("currentDate") LocalDate currentDate);
    
    // Check if semester name exists
    boolean existsBySemesterName(String semesterName);
    
    // Order by start date descending
    List<Semesters> findAllByOrderByStartDateDesc();
}
