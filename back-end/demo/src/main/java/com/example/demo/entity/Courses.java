package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Courses")
public class Courses {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseID", nullable = false)
    private Integer courseId;
    
    @Column(name = "SemesterID")
    private Integer semesterId;
    
    @Column(name = "CourseCode", nullable = false, length = 20)
    private String courseCode;
    
    @Column(name = "CourseName", length = 100)
    private String courseName;
    
    @Column(name = "LecturerID")
    private Integer lecturerId;
    
    @ManyToOne
    @JoinColumn(name = "SemesterID", referencedColumnName = "SemesterID", insertable = false, updatable = false)
    private Semesters semester;
    
    @ManyToOne
    @JoinColumn(name = "LecturerID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users lecturer;
    
    // Constructors
    public Courses() {}
    
    public Courses(Integer semesterId, String courseCode, String courseName, Integer lecturerId) {
        this.semesterId = semesterId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturerId = lecturerId;
    }
    
    // Getters and Setters
    public Integer getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    
    public Integer getSemesterId() {
        return semesterId;
    }
    
    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public Integer getLecturerId() {
        return lecturerId;
    }
    
    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }
    
    public Semesters getSemester() {
        return semester;
    }
    
    public void setSemester(Semesters semester) {
        this.semester = semester;
    }
    
    public Users getLecturer() {
        return lecturer;
    }
    
    public void setLecturer(Users lecturer) {
        this.lecturer = lecturer;
    }
    
    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", semesterId=" + semesterId +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", lecturerId=" + lecturerId +
                '}';
    }
}
