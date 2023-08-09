package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CoursesRepository extends JpaRepository<Course,Integer> {

    List<Course> findByInstructorId(int instructorId);
}
