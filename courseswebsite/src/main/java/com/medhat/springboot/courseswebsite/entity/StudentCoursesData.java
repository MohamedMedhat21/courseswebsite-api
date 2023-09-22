package com.medhat.springboot.courseswebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="student_courses_data")
//@JsonIgnoreProperties({"userId","userName"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCoursesData {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String userName;

    @Column(name="course_name")
    private String courseName;

    @Column(name="course_id")
    private Integer courseId;

    @Column(name="enrollment_date")
    private Date enrollmentDate;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="users_id")
    private Integer userId;

    public StudentCoursesData(Integer id, String userName, String courseName, Integer courseId, Date enrollmentDate, Integer userId) {
        this.id = id;
        this.userName = userName;
        this.courseName = courseName;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.userId = userId;
    }

}
