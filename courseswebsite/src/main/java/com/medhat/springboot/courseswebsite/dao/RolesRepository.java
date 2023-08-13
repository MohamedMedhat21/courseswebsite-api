package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RolesRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);

}
