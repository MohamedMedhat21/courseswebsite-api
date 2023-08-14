package com.medhat.springboot.courseswebsite.rest;


import com.medhat.springboot.courseswebsite.entity.Role;
import com.medhat.springboot.courseswebsite.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RolesRestController {

    private RolesService rolesService;

    @Autowired
    public RolesRestController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/roles")
    public List<Role> findAll(){
        return rolesService.getAll();
    }

    @GetMapping("/roles/{roleId}")
    public Role findById(@PathVariable int roleId){
        return rolesService.getById(roleId);
    }

    @PostMapping("/roles")
    public Role addRole(@RequestBody Role role){
        rolesService.saveRole(role);
        return role;
    }

    @PutMapping("/roles")
    public Role updateRole(@RequestBody Role role){
        rolesService.saveRole(role);
        return role;
    }

    @DeleteMapping("/roles/{roleId}")
    public int deleteById(@PathVariable int roleId){
        rolesService.deleteById(roleId);
        return roleId;
    }

}
