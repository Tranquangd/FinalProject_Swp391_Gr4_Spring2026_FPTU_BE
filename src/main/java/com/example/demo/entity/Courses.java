package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Courses")
public class Courses {

    @Column(name = "CourseID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseid;

    @Column(name = "SemesterID", nullable = true)
    private Integer semesterid;

    @Column(name = "CourseCode", nullable = false)
    private String coursecode;

    @Column(name = "CourseName", nullable = true)
    private String coursename;

    @Column(name = "LecturerID", nullable = true)
    private Integer lecturerid;

    @ManyToOne
    @JoinColumn(name = "SemesterID", referencedColumnName = "SemesterID")
    private Semesters semesters;

    @ManyToOne
    @JoinColumn(name = "LecturerID", referencedColumnName = "UserID")
    private Users users;

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getSemesterid() {
        return semesterid;
    }

    public void setSemesterid(Integer semesterid) {
        this.semesterid = semesterid;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Integer getLecturerid() {
        return lecturerid;
    }

    public void setLecturerid(Integer lecturerid) {
        this.lecturerid = lecturerid;
    }

}
