package com.example.demo.controller;

import com.example.demo.entity.Semesters;
import com.example.demo.service.SemestersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/semesters")
@CrossOrigin(origins = "*")
public class SemestersController {
    
    @Autowired
    private SemestersService semestersService;
    
    // GET /api/semesters - Get all semesters
    @GetMapping
    public ResponseEntity<?> getAllSemesters() {
        try {
            List<Semesters> semesters = semestersService.getAllSemesters();
            return ResponseEntity.ok(semesters);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get semesters: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/semesters/{id} - Get semester by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSemesterById(@PathVariable Integer id) {
        try {
            Optional<Semesters> semester = semestersService.getSemesterById(id);
            if (semester.isPresent()) {
                return ResponseEntity.ok(semester.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Semester not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get semester: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/semesters/name/{semesterName} - Get semester by name
    @GetMapping("/name/{semesterName}")
    public ResponseEntity<?> getSemesterByName(@PathVariable String semesterName) {
        try {
            Optional<Semesters> semester = semestersService.getSemesterByName(semesterName);
            if (semester.isPresent()) {
                return ResponseEntity.ok(semester.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Semester not found with name: " + semesterName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get semester: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/semesters/active?date={date} - Get active semesters by date
    @GetMapping("/active")
    public ResponseEntity<?> getActiveSemestersByDate(@RequestParam(required = false) String date) {
        try {
            LocalDate searchDate = date != null ? LocalDate.parse(date) : LocalDate.now();
            List<Semesters> semesters = semestersService.getActiveSemestersByDate(searchDate);
            return ResponseEntity.ok(semesters);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get active semesters: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // GET /api/semesters/search?name={name} - Search semesters by name
    @GetMapping("/search")
    public ResponseEntity<?> searchSemesters(@RequestParam String name) {
        try {
            List<Semesters> semesters = semestersService.searchSemestersByName(name);
            return ResponseEntity.ok(semesters);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to search semesters: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // POST /api/semesters - Create new semester
    @PostMapping
    public ResponseEntity<?> createSemester(@RequestBody Semesters semester) {
        try {
            Semesters createdSemester = semestersService.createSemester(semester);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSemester);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create semester: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // PUT /api/semesters/{id} - Update semester
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSemester(@PathVariable Integer id, @RequestBody Semesters semesterDetails) {
        try {
            Semesters updatedSemester = semestersService.updateSemester(id, semesterDetails);
            return ResponseEntity.ok(updatedSemester);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update semester: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // DELETE /api/semesters/{id} - Delete semester
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSemester(@PathVariable Integer id) {
        try {
            semestersService.deleteSemester(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Semester deleted successfully");
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete semester: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
