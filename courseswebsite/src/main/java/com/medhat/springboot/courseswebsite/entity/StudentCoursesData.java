package com.medhat.springboot.courseswebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="student_courses_data")
@JsonIgnoreProperties({"userId","userName"})
public class StudentCoursesData {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String userName;

    @Column(name="course_name")
    private String courseName;

    @Column(name="enrollment_date")
    private Date enrollmentDate;

    @Column(name="user_id")
    private int userId;

    public StudentCoursesData() {

    }

    public StudentCoursesData(int id, String userName, String courseName, Date enrollmentDate, int userId) {
        this.id = id;
        this.userName = userName;
        this.courseName = courseName;
        this.enrollmentDate = enrollmentDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StudentCoursesData{" +
                "id=" + id +
                ", userName=" + userName +
                ", courseName=" + courseName +
                ", enrollmentDate=" + enrollmentDate +
                ", userId=" + userId +
                '}';
    }
}
