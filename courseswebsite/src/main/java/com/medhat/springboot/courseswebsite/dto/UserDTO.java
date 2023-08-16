package com.medhat.springboot.courseswebsite.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private Integer personalId;
    private String userName;
    private String email;
    private String role;

}
