package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.auth.RegisterRequest;
import com.medhat.springboot.courseswebsite.dao.RolesRepository;
import com.medhat.springboot.courseswebsite.dto.CourseDTO;
import com.medhat.springboot.courseswebsite.dto.UserDTO;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityPermissions;
import com.medhat.springboot.courseswebsite.service.*;
import com.medhat.springboot.courseswebsite.utils.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private UsersService usersService;
    private ReportService reportService;
    private RolesService rolesService;

    private CoursesService coursesService;

    private StudentCoursesService studentCoursesService;

    private RolesRepository rolesRepository;

    @Autowired
    public UsersRestController(UsersService usersService, ReportService reportService, RolesService rolesService, CoursesService coursesService, StudentCoursesService studentCoursesService, RolesRepository rolesRepository) {
        this.usersService = usersService;
        this.reportService = reportService;
        this.rolesService = rolesService;
        this.coursesService = coursesService;
        this.studentCoursesService = studentCoursesService;
        this.rolesRepository = rolesRepository;
    }


    @GetMapping("/users")
    public List<Users> getAll(){
        return usersService.getAll();
    }

    @GetMapping("/users/exportAll")
    public ResponseEntity<byte[]> exportAllUsers() throws JRException, FileNotFoundException {

        // Export the Jasper Report to a byte array
        byte[] reportInBytes = reportService.exportReport(usersService.getAll());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "users.pdf");

        return new ResponseEntity<>(reportInBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/users/{userId}")
    public UserDTO getById(@PathVariable int userId) {

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN")){
            Users users = usersService.getById(userId);
            UserDTO userDTO = new UserDTO(userId,users.getUsername(),users.getEmail(),users.getAuthorities().toString());
            return userDTO;
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }
    }


    @GetMapping("/users/{userId}/mycourses")
    public List<CourseDTO> getInstructorCourses(@PathVariable int userId){
        Users user = usersService.getById(userId);
        if(WebSecurityPermissions.hasPermission(user.getUsername(),"ADMIN")){

            List<Course> courses = usersService.getInstructorCourses(userId);

            List<CourseDTO> courseDTOS = new ArrayList<>();

            for (Course course:courses) {
                CourseDTO courseDTO = new CourseDTO(course.getId(),course.getName(),course.getDescription(), user.getUsername());
                courseDTOS.add(courseDTO);
            }

            return courseDTOS;
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/mycourses/{courseId}")
    public Course getInstructorCourseById(@PathVariable int userId,@PathVariable int courseId){

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN")){
            Course course = usersService.getInstructorCourseById(courseId);
            if(userId!=course.getInstructorId())
                throw new NotFoundException("Course with Id: "+courseId+" not found or created by another instructor");
            return course;
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users/{userId}/mycourses")
    public Course addInstructorCourse(@PathVariable int userId, @RequestBody Course course){
        if(WebSecurityPermissions.hasRole("INSTRUCTOR")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUsername())){
            course.setInstructorId(userId);
            return usersService.addInstructorCourse(course);
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PutMapping("/users/{userId}/mycourses")
    public Course updateInstructorCourse(@PathVariable int userId, @RequestBody Course course){
        Course dbCourse = coursesService.getById(course.getId());
        if(WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUsername())&&WebSecurityPermissions.hasRole("INSTRUCTOR")&&dbCourse!=null){
            course.setInstructorId(userId);
            return usersService.addInstructorCourse(course);
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @DeleteMapping("/users/{userId}/mycourses/{courseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInstructorCourseById(@PathVariable int userId,@PathVariable int courseId){

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN"))
            usersService.deleteInstructorCourseById(courseId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/enrollments")
    public List<StudentCoursesData> getEnrolledCourses(@PathVariable int userId){
        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN"))
            return usersService.getEnrolledCourses(userId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @GetMapping("/users/{userId}/enrollments/{courseId}")
    public StudentCoursesData getEnrolledCourses(@PathVariable int userId,@PathVariable int courseId){
        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN"))
            return usersService.getEnrolledCourseByCourseId(courseId);
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users/{userId}/enrollments")
    public StudentCourses addEnrolledCourses(@PathVariable int userId,@RequestBody Map<String, Object> payload){
        if(WebSecurityPermissions.hasRole("STUDENT")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUsername())){
            Course course = coursesService.getById(Integer.parseInt(payload.get("courseId").toString()));

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

        if(WebSecurityPermissions.hasRole("STUDENT")&&WebSecurityPermissions.isCurrentUser(usersService.getById(userId).getUsername())){

            studentCoursesService.deleteById(enrollmentId);
        }
        else
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
    }

    @PostMapping("/users")
    public Users addUser(@RequestBody RegisterRequest request){
        String p = new String(new BCryptPasswordEncoder().encode(request.getPassword()));
        Users users = new Users(request.getUsername(),p,request.getEmail(), Constants.DEFAULT_NEW_USER_ENABLED,rolesRepository.findByName(request.getRolename()).get());
        Users currentUser = null;
        try {
            usersService.getByUserName(users.getUsername());
        }
        catch (RuntimeException exception){
            currentUser = usersService.saveUser(users);
        }
        if (currentUser==null)
            throw new RuntimeException("username is already in use, please choose another one");
        return currentUser;
    }

    @PutMapping("/users")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody Map<String,Object> payload){
        Users users = new Users(payload.get("username").toString(),"",payload.get("email").toString(),Integer.parseInt(payload.get("enabled").toString()),rolesService.getByName(payload.get("rolename").toString()));
        users.setId(Integer.parseInt(payload.get("id").toString()));
        Users dbUsers = usersService.getById(users.getId());

        boolean proceed = true;

        System.out.println(payload);
        // check if edited user is an admin as if yes = block ,no admin can't be edited except for himself
        if(dbUsers.getRole().getName().equals("ADMIN")){
            if(!WebSecurityPermissions.isCurrentUser(users.getUsername()))
                proceed=false;
        }

        if(WebSecurityPermissions.hasPermission(dbUsers.getUsername(),"ADMIN")&&proceed){
            users.setPassword(dbUsers.getPassword());

            // check if the current user is an admin as if yes = he can edit users roles else he can't
            if(users.getRole().getId()!=dbUsers.getRole().getId()&&!WebSecurityPermissions.hasRole("ADMIN")){
                throw new NotAuthorizedException("You can't edit user roles unless you are an admin");
            }

            // check if the current user is an admin as if yes = he can edit enabled value else he can't
            if(users.getEnabled()!=dbUsers.getEnabled()&&!WebSecurityPermissions.hasRole("ADMIN")){
                throw new NotAuthorizedException("You can't edit enabled value unless you are an admin");
            }

            if(!users.getUsername().equals(dbUsers.getUsername())){
                throw new NotAuthorizedException("You can't edit username");
            }

            usersService.saveUser(users);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }

    }

    @DeleteMapping("/users/{userId}")
    public int deleteById(@PathVariable int userId){

        boolean proceed = true;

        if(usersService.getById(userId).getRole().getId() == rolesService.getByName("ADMIN").getId())
            proceed=false;

        if(WebSecurityPermissions.hasPermission(usersService.getById(userId).getUsername(),"ADMIN")&&proceed)
        {
            usersService.deleteById(userId);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
        }

        return userId;
    }

}
