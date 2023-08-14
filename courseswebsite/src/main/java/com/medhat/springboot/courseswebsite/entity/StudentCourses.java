package com.medhat.springboot.courseswebsite.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="student_courses")
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="course_id")
    private Integer courseId;

    @Column(name="users_id")
    private Integer userId;

    @Column(name="enrollment_date")
    private Date enrollmentDate;

    public StudentCourses() {

    }

    public StudentCourses(Integer courseId, Integer userId, Date enrollmentDate) {
        this.courseId = courseId;
        this.userId = userId;
        this.enrollmentDate = enrollmentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "StudentCourses{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", userId=" + userId +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
