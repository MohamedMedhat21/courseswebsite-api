package com.medhat.springboot.courseswebsite.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="student_courses")
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="course_id")
    private int courseId;

    @Column(name="user_id")
    private int userId;

    @Column(name="enrollment_date")
    private Date enrollmentDate;

    public StudentCourses() {

    }

    public StudentCourses(int courseId, int userId, Date enrollmentDate) {
        this.courseId = courseId;
        this.userId = userId;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
