package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoursesRestController {

    private CoursesService coursesService;

    @Autowired
    public CoursesRestController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/courses")
    public List<Course> findAll(){
        return coursesService.getAll();
    }

    @GetMapping("/courses/{courseId}")
    public Course findById(@PathVariable int courseId){
        return coursesService.getById(courseId);
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        course.setId(0);
        coursesService.saveCourse(course);
        return course;
    }

    @PutMapping("/courses")
    public Course updateCourse(@RequestBody Course course){
        coursesService.saveCourse(course);
        return course;
    }

    @DeleteMapping("/courses/{courseId}")
    public int deleteById(@PathVariable int courseId){
        coursesService.deleteById(courseId);
        return courseId;
    }

}
