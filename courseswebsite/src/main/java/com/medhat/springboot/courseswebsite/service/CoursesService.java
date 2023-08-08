package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;

import java.util.List;

public interface CoursesService {
    public List<Course> findAll();
    public Course findById(int courseId);
    public void saveCourse(Course course);
    public void deleteById(int courseId);
}
