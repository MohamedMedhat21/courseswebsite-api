package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.StudentCourses;

import java.util.List;

public interface StudentCoursesService {
    public List<StudentCourses> getAll();
    public StudentCourses getById(int Id);
    public StudentCourses saveStudentCourse(StudentCourses studentCourses);
    public void deleteById(int Id);
}
