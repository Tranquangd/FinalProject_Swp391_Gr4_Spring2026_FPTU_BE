package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Semesters")
public class Semesters {

    @Column(name = "SemesterID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer semesterid;

    @Column(name = "SemesterName", nullable = false)
    private String semestername;

    @Column(name = "StartDate", nullable = true)
    private LocalDate startdate;

    @Column(name = "EndDate", nullable = true)
    private LocalDate enddate;

    // Constructors
    public Semesters() {
    }

    // Getters and Setters
    public Integer getSemesterid() {
        return semesterid;
    }

    public void setSemesterid(Integer semesterid) {
        this.semesterid = semesterid;
    }

    public String getSemestername() {
        return semestername;
    }

    public void setSemestername(String semestername) {
        this.semestername = semestername;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }
}
