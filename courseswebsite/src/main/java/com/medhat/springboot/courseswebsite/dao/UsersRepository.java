package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<User,Integer> {

}
