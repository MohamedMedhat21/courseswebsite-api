package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.dto.CourseDTO;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.ListResponse;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import com.medhat.springboot.courseswebsite.securingweb.WebSecurityPermissions;
import com.medhat.springboot.courseswebsite.utils.Constants;
import com.medhat.springboot.courseswebsite.service.CoursesService;
import com.medhat.springboot.courseswebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin
public class CoursesRestController {

    private CoursesService coursesService;

    private UsersService usersService;

    @Autowired
    public CoursesRestController(CoursesService coursesService,UsersService usersService) {
        this.coursesService = coursesService;
        this.usersService = usersService;
    }

    @GetMapping("/courses")
    public ResponseEntity<ListResponse> findAll(@RequestParam("pageNumber") int pageNumber,@RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField){
        Pageable paginationSettings = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
        Page<Course> modelPage = coursesService.getAll(paginationSettings);
        List<Course> courses = modelPage.getContent();

        int totalPages = modelPage.getTotalPages();


        ListResponse responseList = new ListResponse();
        responseList.setCourses(courses);
        responseList.setPage(pageNumber);
        responseList.setTotalPages(totalPages);
        responseList.setTotalRecords((int)modelPage.getTotalElements());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/courses/{courseId}")
    public CourseDTO findById(@PathVariable int courseId){

        Course course = coursesService.getById(courseId);

        CourseDTO courseDTO = new CourseDTO(courseId,course.getName(),course.getDescription(),usersService.getById(course.getInstructorId()).getUsername());

        return courseDTO;
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        if(WebSecurityPermissions.hasRole("INSTRUCTOR")){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            course.setInstructorId(usersService.getByUserName(currentPrincipalName).getId());
            course.setCreationDate(new Date());
            coursesService.saveCourse(course);
        }
        else{
            throw new NotAuthorizedException("Access Denied, you don't have permissions to post a course unless you are an instructor");
        }
        return course;
    }

//    @PutMapping("/courses")
//    public Course updateCourse(@RequestBody Course course){
//        Course dbCourse = coursesService.getById(course.getId());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//
//        if(WebSecurityPermissions.hasRole("INSTRUCTOR")){
//            if(dbCourse.getInstructorId() == auth)
//            coursesService.saveCourse(course);
//        }
//        else{
//            throw new NotAuthorizedException("Access Denied, you don't have permissions to access other users data");
//        }
//        return course;
//    }
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
    //should be implemented in the user rest

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
