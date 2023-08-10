package com.medhat.springboot.courseswebsite.securingweb;

import com.medhat.springboot.courseswebsite.exception.NotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class WebSecurityPermissions {


    public WebSecurityPermissions() {
    }

    public static boolean hasPermission(Principal principal,int userId,String currentUserName){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        boolean isCurrentUser= principal.getName().equals(currentUserName);

        if(hasAdminRole || isCurrentUser)
            return true;
        else{
            return false;
        }

    }

}
