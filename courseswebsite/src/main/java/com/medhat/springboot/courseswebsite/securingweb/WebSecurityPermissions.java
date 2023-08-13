package com.medhat.springboot.courseswebsite.securingweb;

import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class WebSecurityPermissions {


    public WebSecurityPermissions() {
    }

    public static boolean hasPermission(Principal principal,String currentUserName,String roleToCheck){

        if(hasRole(roleToCheck) || isCurrentUser(principal,currentUserName))
            return true;
        else{
            return false;
        }

    }

    public static boolean isCurrentUser(Principal principal,String currentUserName){

        boolean isCurrentUsr= principal.getName().equals(currentUserName);

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
