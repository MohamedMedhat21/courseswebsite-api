package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByUserName(String userName);

}
