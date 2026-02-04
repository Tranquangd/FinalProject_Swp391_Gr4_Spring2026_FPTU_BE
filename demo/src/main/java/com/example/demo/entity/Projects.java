package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Projects")
public class Projects {

    @Column(name = "ProjectID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectid;

    @Column(name = "CourseID", nullable = true)
    private Integer courseid;

    @Column(name = "GroupName", nullable = true)
    private String groupname;

    @Column(name = "ProjectName", nullable = true)
    private String projectname;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "ProjectStatus", nullable = true)
    private String projectstatus;

    @Column(name = "CreatedAt", nullable = true)
    private LocalDateTime createdat;

    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", insertable = false, updatable = false)
    private Courses courses;

    // Constructors
    public Projects() {
    }

    // Getters and Setters
    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectstatus() {
        return projectstatus;
    }

    public void setProjectstatus(String projectstatus) {
        this.projectstatus = projectstatus;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
