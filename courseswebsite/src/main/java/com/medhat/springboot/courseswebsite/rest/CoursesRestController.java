package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.dto.CourseDTO;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityPermissions;
import com.medhat.springboot.courseswebsite.service.CoursesService;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoursesRestController {

    private CoursesService coursesService;

    private UsersService usersService;

    @Autowired
    public CoursesRestController(CoursesService coursesService,UsersService usersService) {
        this.coursesService = coursesService;
        this.usersService = usersService;
    }

    @GetMapping("/courses")
    public List<Course> findAll(){
        return coursesService.getAll();
    }

    @GetMapping("/courses/{courseId}")
    public CourseDTO findById(@PathVariable int courseId){

        Course course = coursesService.getById(courseId);

        CourseDTO courseDTO = new CourseDTO(courseId,course.getName(),course.getDescription());

        return courseDTO;
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        if(WebSecurityPermissions.hasRole("INSTRUCTOR")){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            course.setInstructorId(usersService.getByUserName(currentPrincipalName).getId());
            coursesService.saveCourse(course);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }
        return course;
    }

//    @PutMapping("/courses")
//    public Course updateCourse(@RequestBody Course course){
//        if(WebSecurityPermissions.hasRole("INSTRUCTOR")){
//            coursesService.saveCourse(course);
//        }
//        else{
//            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
//        }
//        return course;
//    }

    @DeleteMapping("/courses/{courseId}")
    public int deleteById(@PathVariable int courseId){
        if(WebSecurityPermissions.hasRole("ADMIN")){
            coursesService.deleteById(courseId);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }
        return courseId;
    }

}
