package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityConfig;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityPermissions;
import com.medhat.springboot.courseswebsite.service.CoursesService;
import com.medhat.springboot.courseswebsite.service.RolesService;
import com.medhat.springboot.courseswebsite.service.StudentCoursesService;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private UsersService usersService;
    private RolesService rolesService;

    private CoursesService coursesService;

    private StudentCoursesService studentCoursesService;

    @Autowired
    public UsersRestController(UsersService usersService,RolesService rolesService,CoursesService coursesService,StudentCoursesService studentCoursesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
        this.coursesService = coursesService;
        this.studentCoursesService = studentCoursesService;
    }

    @GetMapping("/users")
    public List<Users> getAll(){
        return usersService.getAll();
    }

    @GetMapping("/users/{userId}")
    public Users getById(@PathVariable int userId, Principal principal) {

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getById(userId);
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }
    }


    @GetMapping("/users/{userId}/mycourses")
    public List<Course> getInstructorCourses(@PathVariable int userId,Principal principal){
        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getInstructorCourses(userId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/mycourses/{courseId}")
    public Course getInstructorCourseById(@PathVariable int userId,@PathVariable int courseId,Principal principal){

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getInstructorCourseById(courseId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users/{userId}/mycourses")
    public Course addInstructorCourse(@PathVariable int userId, @RequestBody Course course){
        if(WebSecurityPermissions.hasRole("INSTRUCTOR")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUserName())){
            course.setInstructorId(userId);
            return usersService.addInstructorCourse(course);
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PutMapping("/users/{userId}/mycourses")
    public Course updateInstructorCourse(@PathVariable int userId, @RequestBody Course course){
        Course dbCourse = coursesService.getById(course.getId());
        if(WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUserName())&&WebSecurityPermissions.hasRole("INSTRUCTOR")&&dbCourse!=null){
            course.setInstructorId(userId);
            return usersService.addInstructorCourse(course);
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @DeleteMapping("/users/{userId}/mycourses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInstructorCourseById(@PathVariable int userId,@PathVariable int courseId,Principal principal){

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            usersService.deleteInstructorCourseById(courseId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<StudentCoursesData> getEnrolledCourses(@PathVariable int userId,Principal principal){
        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getEnrolledCourses(userId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/enrollments/{courseId}")
    public StudentCoursesData getEnrolledCourses(@PathVariable int userId,@PathVariable int courseId, Principal principal){
        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN"))
            return usersService.getEnrolledCourseByCourseId(courseId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users/{userId}/enrollments")
    public StudentCourses addEnrolledCourses(@PathVariable int userId,@RequestBody Map<String, Object> payload){
        if(WebSecurityPermissions.hasRole("STUDENT")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUserName())){
            Course course = coursesService.findByName(payload.get("courseName").toString());

            if (course==null)
                throw new NotFoundException("course with name: "+ payload.get("courseName").toString()+" not found");

            List<StudentCoursesData> userCourses = usersService.getEnrolledCourses(userId);

            if (userCourses.stream().filter(item -> Objects.equals(item.getCourseId(), course.getId())).count() != 0){
                throw new RuntimeException("you are already enrolled in this course");
            }

            StudentCourses studentCourses = new StudentCourses();

            studentCourses.setId(0);
            studentCourses.setCourseId(course.getId());
            studentCourses.setUserId(userId);
            studentCourses.setEnrollmentDate(Date.from(Instant.now()));
            return studentCoursesService.saveStudentCourse(studentCourses);
//            System.out.println(payload.get("courseName").toString());
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @DeleteMapping("/users/{userId}/enrollments/{enrollmentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStudentEnrollmentById(@PathVariable int userId,@PathVariable int enrollmentId){

        if(WebSecurityPermissions.hasRole("STUDENT")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUserName())){

            studentCoursesService.deleteById(enrollmentId);
        }
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
            if(!WebSecurityPermissions.isCurrentUser(users.getUserName()))
                proceed=false;
        }

        if(WebSecurityPermissions.hasPermission(usersService.getById(users.getId()).getUserName(),"ADMIN")&&proceed){
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

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUserName(),"ADMIN")&&proceed)
        {
                usersService.deleteById(userId);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }

        return userId;
    }

}
