package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentCoursesRepository extends JpaRepository<StudentCourses,Integer> {
//    List<StudentCourses> findByUserId(int userId);
}
