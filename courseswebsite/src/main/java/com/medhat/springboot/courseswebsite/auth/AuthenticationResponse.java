package com.medhat.springboot.courseswebsite.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;
  private Integer expiresAfter;
  private Integer userId;
  private Integer roleId;
}
