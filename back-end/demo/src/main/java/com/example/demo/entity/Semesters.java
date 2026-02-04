package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Semesters")
public class Semesters {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SemesterID", nullable = false)
    private Integer semesterId;
    
    @Column(name = "SemesterName", nullable = false, length = 50)
    private String semesterName;
    
    @Column(name = "StartDate")
    private LocalDate startDate;
    
    @Column(name = "EndDate")
    private LocalDate endDate;
    
    // Constructors
    public Semesters() {}
    
    public Semesters(String semesterName, LocalDate startDate, LocalDate endDate) {
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getters and Setters
    public Integer getSemesterId() {
        return semesterId;
    }
    
    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }
    
    public String getSemesterName() {
        return semesterName;
    }
    
    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        return "Semesters{" +
                "semesterId=" + semesterId +
                ", semesterName='" + semesterName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
