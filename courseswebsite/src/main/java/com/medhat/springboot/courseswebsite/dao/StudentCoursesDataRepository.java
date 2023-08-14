package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentCoursesDataRepository extends JpaRepository<StudentCoursesData,Integer> {
    List<StudentCoursesData> findByUserId(int userId);

    StudentCoursesData findByCourseId(int courseId);
}
