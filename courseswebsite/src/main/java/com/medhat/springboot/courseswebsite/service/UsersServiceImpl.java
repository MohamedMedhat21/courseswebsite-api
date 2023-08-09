package com.medhat.springboot.courseswebsite.service;


import com.medhat.springboot.courseswebsite.dao.CoursesRepository;
import com.medhat.springboot.courseswebsite.dao.StudentCoursesDataRepository;
import com.medhat.springboot.courseswebsite.dao.StudentCoursesRepository;
import com.medhat.springboot.courseswebsite.dao.UsersRepository;
import com.medhat.springboot.courseswebsite.entity.Course;
import com.medhat.springboot.courseswebsite.entity.StudentCourses;
import com.medhat.springboot.courseswebsite.entity.StudentCoursesData;
import com.medhat.springboot.courseswebsite.entity.User;
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
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public List<Course> findInstructorCourses(int userId) {
        return coursesRepository.findByInstructorId(userId);
    }

    @Override
    public List<StudentCoursesData> findEnrolledCourses(int userId) {

        List<StudentCoursesData> studentCoursesData = studentCoursesDataRepository.findByUserId(userId);

//        System.out.println(studentCoursesData.get(0));

        return studentCoursesData;
    }

    @Override
    @Transactional
    public User findById(int userId) {
        Optional<User> result = usersRepository.findById(userId);

        User user;

        if(result.isPresent())
            user =  result.get();
        else
            throw new RuntimeException("User with Id:"+userId+" not found");

        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(int userId) {
        usersRepository.deleteById(userId);
    }
}
