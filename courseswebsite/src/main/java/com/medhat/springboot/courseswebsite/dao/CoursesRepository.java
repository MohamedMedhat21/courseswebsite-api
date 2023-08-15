package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CoursesRepository extends JpaRepository<Course,Integer> {

    Page<Course> findAll(Pageable pageable);
    List<Course> findByInstructorId(int instructorId);
    Course findByName(String name);
}

