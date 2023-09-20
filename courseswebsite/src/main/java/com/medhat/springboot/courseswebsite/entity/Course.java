package com.medhat.springboot.courseswebsite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;
    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="total_hours")
    private Integer totalHours;

    @Column(name="headline")
    private String headline;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="course_link")
    private String courseLink;

    @Column(name="instructor_id")
    private Integer instructorId;



}
