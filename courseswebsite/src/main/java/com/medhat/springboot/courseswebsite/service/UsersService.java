package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.User;

import java.util.List;

public interface UsersService {
    public List<User> findAll();
    public User findById(int userId);
    public void saveUser(User user);
    public void deleteById(int userId);

    public List<Course> findInstructorCourses(int userId);
}
