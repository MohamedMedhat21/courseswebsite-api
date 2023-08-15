package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);

}
