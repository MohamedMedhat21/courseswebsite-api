package com.medhat.springboot.courseswebsite.auth;

import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.service.UsersService;
import com.medhat.springboot.courseswebsite.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UsersService usersService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    boolean found;
    try {
      usersService.getByUserName(request.getUsername());
      found=true;
    }
    catch (RuntimeException exception){
      found=false;
    }
    if (found)
      throw new RuntimeException("username is already in use, please choose another one");

    AuthenticationResponse authRes = service.register(request);
    Users currUser = usersService.getByUserName(request.getUsername());
    authRes.setUserId(currUser.getId());
    authRes.setExpiresAfter(Constants.JWT_TOKEN_VALID_TIME_IN_MINUTES);
    authRes.setRoleId(currUser.getRole().getId());
    return ResponseEntity.ok(authRes);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    AuthenticationResponse authRes = service.authenticate(request);
    Users currUser = usersService.getByUserName(request.getUsername());
    authRes.setUserId(currUser.getId());
    authRes.setExpiresAfter(Constants.JWT_TOKEN_VALID_TIME_IN_MINUTES);
    authRes.setRoleId(currUser.getRole().getId());
    return ResponseEntity.ok(authRes);
  }


}
