package com.medhat.springboot.courseswebsite.service;


import com.medhat.springboot.courseswebsite.dao.RolesRepository;
import com.medhat.springboot.courseswebsite.entity.Role;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepository;

    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    @Transactional
    public List<Role> getAll() {
        return rolesRepository.findAll();
    }

    @Override
    @Transactional
    public Role getById(int roleId) {
        Optional<Role> result = rolesRepository.findById(roleId);

        Role role;

        if(result.isPresent())
            role =  result.get();
        else
            throw new NotFoundException("Role with Id:"+roleId+" not found");

        return role;
    }

    @Override
    @Transactional
    public Role getByName(String roleName) {
        Role result = rolesRepository.findByName(roleName).get();

        return result;
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return rolesRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteById(int userId) {
        rolesRepository.deleteById(userId);
    }
}
