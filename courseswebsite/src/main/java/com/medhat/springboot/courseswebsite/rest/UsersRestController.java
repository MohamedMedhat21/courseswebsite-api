package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityConfig;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityPermissions;
import com.medhat.springboot.courseswebsite.service.RolesService;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private UsersService usersService;
    private RolesService rolesService;

    @Autowired
    public UsersRestController(UsersService usersService,RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    @GetMapping("/users")
    public List<Users> getAll(){
        return usersService.getAll();
    }

    @GetMapping("/users/{userId}")
    public Users getById(@PathVariable int userId, Principal principal) {

        if(WebSecurityPermissions.hasPermission(principal,usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getById(userId);
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }
    }


    @GetMapping("/users/{userId}/mycourses")
    public List<Course> getInstructorCourses(@PathVariable int userId,Principal principal){
        if(WebSecurityPermissions.hasPermission(principal,usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getInstructorCourses(userId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<StudentCoursesData> getEnrolledCourses(@PathVariable int userId,Principal principal){
        if(WebSecurityPermissions.hasPermission(principal,usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getEnrolledCourses(userId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users")
    public Users addUser(@RequestBody Users users){

        return usersService.saveUser(users);
    }

    @PutMapping("/users")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody Users users,Principal principal){
        Users dbUsers = usersService.getById(users.getId());

        boolean proceed = true;

        if(usersService.getById(users.getId()).getRoleId() == rolesService.getByName("ADMIN").getId()){
            if(!WebSecurityPermissions.isCurrentUser(principal,users.getUserName()))
                proceed=false;
        }

        if(WebSecurityPermissions.hasPermission(principal,usersService.getById(users.getId()).getUserName(),"ADMIN")&&proceed){
            if (dbUsers !=null){
                users.setPassword(dbUsers.getPassword());
                if(!WebSecurityPermissions.hasRole("ADMIN")){
                    users.setRoleId(dbUsers.getRoleId());
                }
                usersService.saveUser(users);
            }
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }

    }

    @DeleteMapping("/users/{userId}")
    public int deleteById(@PathVariable int userId,Principal principal){

        boolean proceed = true;

        if(usersService.getById(userId).getRoleId() == rolesService.getByName("ADMIN").getId())
            proceed=false;

        if(WebSecurityPermissions.hasPermission(principal,usersService.getById(userId).getUserName(),"ADMIN")&&proceed)
        {
                usersService.deleteById(userId);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }

        return userId;
    }

}
