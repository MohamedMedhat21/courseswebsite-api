package com.medhat.springboot.courseswebsite.service;


import com.medhat.springboot.courseswebsite.dao.CoursesRepository;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CoursesServiceImpl implements CoursesService{

    private CoursesRepository coursesRepository;

    public CoursesServiceImpl(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    @Transactional
    public List<Course> getAll() {
        return coursesRepository.findAll();
    }

    @Override
    @Transactional
    public Course getById(int courseId) {
        Optional<Course> result = coursesRepository.findById(courseId);

        Course course;

        if(result.isPresent())
            course =  result.get();
        else
            throw new NotFoundException("Course with Id:"+courseId+" not found");

        return course;
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        coursesRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(int courseId) {
        coursesRepository.deleteById(courseId);
    }
}
