package com.example.demo.service;

import com.example.demo.entity.Semesters;
import com.example.demo.repository.SemestersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SemestersService {
    
    @Autowired
    private SemestersRepository semestersRepository;
    
    // Get all semesters
    public List<Semesters> getAllSemesters() {
        return semestersRepository.findAll();
    }
    
    // Get semester by ID
    public Optional<Semesters> getSemesterById(Integer id) {
        return semestersRepository.findById(id);
    }
    
    // Get semester by name
    public Optional<Semesters> getSemesterByName(String semesterName) {
        return semestersRepository.findBySemestername(semesterName);
    }
    
    // Get active semesters by date
    public List<Semesters> getActiveSemestersByDate(LocalDate date) {
        return semestersRepository.findActiveSemestersByDate(date);
    }
    
    // Search semesters by name
    public List<Semesters> searchSemestersByName(String name) {
        return semestersRepository.searchByName(name);
    }
    
    // Create new semester
    public Semesters createSemester(Semesters semester) {
        // Check if semester name already exists
        if (semester.getSemestername() != null && semestersRepository.findBySemestername(semester.getSemestername()).isPresent()) {
            throw new RuntimeException("Semester name already exists: " + semester.getSemestername());
        }
        
        // Validate date range
        if (semester.getStartdate() != null && semester.getEnddate() != null) {
            if (semester.getStartdate().isAfter(semester.getEnddate())) {
                throw new RuntimeException("Start date must be before end date");
            }
        }
        
        return semestersRepository.save(semester);
    }
    
    // Update semester
    public Semesters updateSemester(Integer id, Semesters semesterDetails) {
        Semesters semester = semestersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found with id: " + id));
        
        // Update fields
        if (semesterDetails.getSemestername() != null && !semesterDetails.getSemestername().equals(semester.getSemestername())) {
            // Check if new semester name already exists
            if (semestersRepository.findBySemestername(semesterDetails.getSemestername()).isPresent()) {
                throw new RuntimeException("Semester name already exists: " + semesterDetails.getSemestername());
            }
            semester.setSemestername(semesterDetails.getSemestername());
        }
        if (semesterDetails.getStartdate() != null) {
            semester.setStartdate(semesterDetails.getStartdate());
        }
        if (semesterDetails.getEnddate() != null) {
            semester.setEnddate(semesterDetails.getEnddate());
        }
        
        // Validate date range
        if (semester.getStartdate() != null && semester.getEnddate() != null) {
            if (semester.getStartdate().isAfter(semester.getEnddate())) {
                throw new RuntimeException("Start date must be before end date");
            }
        }
        
        return semestersRepository.save(semester);
    }
    
    // Delete semester
    public void deleteSemester(Integer id) {
        Semesters semester = semestersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found with id: " + id));
        semestersRepository.delete(semester);
    }
}
