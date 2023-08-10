package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;

import java.util.List;

public interface UsersService {
    public List<Users> getAll();
    public Users getById(int userId);
    public Users saveUser(Users users);
    public void deleteById(int userId);

    public List<Course> getInstructorCourses(int userId);

    public List<StudentCoursesData> getEnrolledCourses(int userId);
}
