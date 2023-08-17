package com.medhat.springboot.courseswebsite.dao;

import com.medhat.springboot.courseswebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolesRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String name);

}
