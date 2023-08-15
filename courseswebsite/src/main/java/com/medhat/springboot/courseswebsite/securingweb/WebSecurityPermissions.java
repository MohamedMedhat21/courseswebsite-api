package com.medhat.springboot.courseswebsite.securingweb;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasRolee = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(roleToCheck));

        if(hasRolee)
            return true;
        else{
            return false;
        }

    }

}
