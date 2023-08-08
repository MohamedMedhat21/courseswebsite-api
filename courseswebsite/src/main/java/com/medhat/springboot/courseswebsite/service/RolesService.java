package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Role;

import java.util.List;

public interface RolesService {
    public List<Role> findAll();
    public Role findById(int roleId);
    public void saveRole(Role role);

    public void deleteById(int roleId);
}
