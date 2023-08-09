package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.User;

import java.util.List;

public interface UsersService {
    public List<User> getAll();
    public User getById(int userId);
    public User saveUser(User user);
    public void deleteById(int userId);

    public List<Course> getInstructorCourses(int userId);

    public List<StudentCoursesData> getEnrolledCourses(int userId);
}
