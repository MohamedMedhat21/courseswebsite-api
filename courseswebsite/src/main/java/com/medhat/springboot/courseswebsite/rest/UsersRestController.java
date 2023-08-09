package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.User;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsersRestController {

    private UsersService usersService;

    @Autowired
    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return usersService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User findById(@PathVariable int userId){
        return usersService.findById(userId);
    }


    @GetMapping("/users/{userId}/mycourses")
    public List<Course> findInstructorCourses(@PathVariable int userId){
        return usersService.findInstructorCourses(userId);
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<StudentCoursesData> findEnrolledCourses(@PathVariable int userId){
        return usersService.findEnrolledCourses(userId);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        user.setId(0);
        usersService.saveUser(user);
        return user;
    }

    @PutMapping("/users")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user){
        User dbUser = usersService.findById(user.getId());

        if (dbUser!=null)
            usersService.saveUser(user);

    }

    @DeleteMapping("/users/{userId}")
    public int deleteById(@PathVariable int userId){
        usersService.deleteById(userId);
        return userId;
    }

}
