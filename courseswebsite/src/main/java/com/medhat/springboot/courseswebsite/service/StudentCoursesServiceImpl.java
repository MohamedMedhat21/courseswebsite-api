package com.medhat.springboot.courseswebsite.service;


import com.medhat.springboot.courseswebsite.dao.StudentCoursesRepository;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCoursesServiceImpl implements StudentCoursesService{

    private StudentCoursesRepository studentCoursesRepository;

    public StudentCoursesServiceImpl(StudentCoursesRepository studentCoursesRepository) {
        this.studentCoursesRepository = studentCoursesRepository;
    }

    @Override
    @Transactional
    public List<StudentCourses> getAll() {
        return studentCoursesRepository.findAll();
    }

    @Override
    @Transactional
    public StudentCourses getById(int id) {
        Optional<StudentCourses> result = studentCoursesRepository.findById(id);

        StudentCourses studentCourse;

        if(result.isPresent())
            studentCourse =  result.get();
        else
            throw new NotFoundException("enrollment with Id:"+id+" not found");

        return studentCourse;
    }

    @Override
    @Transactional
    public StudentCourses saveStudentCourse(StudentCourses studentCourse) {
       return studentCoursesRepository.save(studentCourse);
    }

    @Override
    @Transactional
    public void deleteById(int Id) {
        studentCoursesRepository.deleteById(Id);
    }
}
