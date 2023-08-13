package com.medhat.springboot.courseswebsite.service;


import com.medhat.springboot.courseswebsite.dao.CoursesRepository;
import com.medhat.springboot.courseswebsite.dao.StudentCoursesDataRepository;
import com.medhat.springboot.courseswebsite.dao.UsersRepository;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private CoursesRepository coursesRepository;

    private StudentCoursesDataRepository studentCoursesDataRepository;

    public UsersServiceImpl(UsersRepository usersRepository,CoursesRepository coursesRepository,StudentCoursesDataRepository studentCoursesDataRepository) {
        this.usersRepository = usersRepository;
        this.coursesRepository = coursesRepository;
        this.studentCoursesDataRepository = studentCoursesDataRepository;
    }

    @Override
    @Transactional
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public List<Course> getInstructorCourses(int userId) {

        getById(userId);

        List<Course> courses = coursesRepository.findByInstructorId(userId);

        if (courses.isEmpty())
            throw new NotFoundException("Instructor with Id:"+userId+" has no courses");

        return courses;
    }

    @Override
    public List<StudentCoursesData> getEnrolledCourses(int userId) {

        getById(userId);

        List<StudentCoursesData> studentCoursesData = studentCoursesDataRepository.findByUserId(userId);

        if (studentCoursesData.isEmpty())
            throw new NotFoundException("Student with Id:"+userId+" not enrolled in any course");

        return studentCoursesData;
    }

    @Override
    public Users getByUserName(String userName) {
        Optional<Users> result = usersRepository.findByUserName(userName);

        Users users;

        if(result.isPresent())
            users =  result.get();
        else
            throw new NotFoundException("User with Id:"+userName+" not found");

        return users;
    }

    @Override
    @Transactional
    public Users getById(int userId) {
        Optional<Users> result = usersRepository.findById(userId);

        Users users;

        if(result.isPresent())
            users =  result.get();
        else
            throw new NotFoundException("User with Id:"+userId+" not found");

        return users;
    }

    @Override
    @Transactional
    public Users saveUser(Users users) {
        return usersRepository.save(users);
    }

    @Override
    @Transactional
    public void deleteById(int userId) {
        usersRepository.deleteById(userId);
    }
}
