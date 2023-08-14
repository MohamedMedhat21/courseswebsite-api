package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    public List<Users> getAll();
    public Users getById(int userId);
    public Users saveUser(Users users);
    public void deleteById(int userId);

    public List<Course> getInstructorCourses(int userId);

    public Course getInstructorCourseById(int courseId);

    public List<StudentCoursesData> getEnrolledCourses(int userId);

    public Users getByUserName(String userName);

    public Course addInstructorCourse(Course course);

    public void deleteInstructorCourseById(int courseId);

    public StudentCoursesData getEnrolledCourseByCourseId(int courseId);

//    void deleteStudentEnrollmentById(int courseId);
}
