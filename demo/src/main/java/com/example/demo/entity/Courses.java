package com.example.demo.entity;

import jakarta.persistence.*;

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
    @JoinColumn(name = "SemesterID", referencedColumnName = "SemesterID", insertable = false, updatable = false)
    private Semesters semesters;

    @ManyToOne
    @JoinColumn(name = "LecturerID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private Users users;

    // Constructors
    public Courses() {
    }

    // Getters and Setters
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

    public Semesters getSemesters() {
        return semesters;
    }

    public void setSemesters(Semesters semesters) {
        this.semesters = semesters;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
