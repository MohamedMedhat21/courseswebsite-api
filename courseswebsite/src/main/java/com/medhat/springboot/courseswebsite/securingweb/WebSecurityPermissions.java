package com.medhat.springboot.courseswebsite.securingweb;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.stream.Collectors;

public class WebSecurityPermissions {


    public WebSecurityPermissions() {
    }

    public static boolean hasPermission(String currentUserName,String roleToCheck){

        if(hasRole(roleToCheck) || isCurrentUser(currentUserName))
            return true;
        else{
            return false;
        }

    }

    public static boolean isCurrentUser(String currentUserName){
        Object principals  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principals;
        boolean isCurrentUsr= userDetails.getUsername().equals(currentUserName);

        if(isCurrentUsr)
            return true;
        else{
            return false;
        }

    }

    public static boolean hasRole(String roleToCheck){

        Object principals = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principals;
        boolean hasRolee = userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(roleToCheck));


        if(hasRolee)
            return true;
        else{
            return false;
        }

    }

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Role = authentication.getAuthorities().stream().collect(Collectors.toList()).get(0).toString();
        return Role;
    }

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Role = authentication.getAuthorities().stream().collect(Collectors.toList()).get(0).toString();
        return Role;
    }
}
