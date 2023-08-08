package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Course;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;


public interface CoursesRepository extends JpaRepository<Course,Integer> {

    List<Course> findByInstructorId(int instructorId);
}
