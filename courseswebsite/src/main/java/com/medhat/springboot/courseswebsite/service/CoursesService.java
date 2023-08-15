package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoursesService {
    public Page<Course> getAll(Pageable pageable);
    public Course getById(int courseId);
    public void saveCourse(Course course);
    public void deleteById(int courseId);

    public Course findByName(String name);
}
