package com.medhat.springboot.courseswebsite.service;

import com.medhat.springboot.courseswebsite.entity.Role;

import java.util.List;

public interface RolesService {
    public List<Role> getAll();
    public Role getById(int roleId);
    public void saveRole(Role role);

    public void deleteById(int roleId);
}
