package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private UsersService usersService;

    @Autowired
    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public List<Users> getAll(){
        return usersService.getAll();
    }

    @GetMapping("/users/{userId}")
    public Users getById(@PathVariable int userId){
        return usersService.getById(userId);
    }


    @GetMapping("/users/{userId}/mycourses")
    public List<Course> getInstructorCourses(@PathVariable int userId){
        return usersService.getInstructorCourses(userId);
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<StudentCoursesData> getEnrolledCourses(@PathVariable int userId){
        return usersService.getEnrolledCourses(userId);
    }

    @PostMapping("/users")
    public Users addUser(@RequestBody Users users){
        return usersService.saveUser(users);
    }

    @PutMapping("/users")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody Users users){
        Users dbUsers = usersService.getById(users.getId());

        if (dbUsers !=null)
            usersService.saveUser(users);

    }

    @DeleteMapping("/users/{userId}")
    public int deleteById(@PathVariable int userId){
        usersService.deleteById(userId);
        return userId;
    }

    @GetMapping("/users/login")
    public String loginUser(){
        return "Welcome!";
    }

}
