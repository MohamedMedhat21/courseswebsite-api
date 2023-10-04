package com.medhat.springboot.courseswebsite.auth;

import com.medhat.springboot.courseswebsite.dao.TokenRepository;
import com.medhat.springboot.courseswebsite.entity.Token;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.exception.NotFoundException;
import com.medhat.springboot.courseswebsite.securingweb.JwtService;
import com.medhat.springboot.courseswebsite.service.UsersService;
import com.medhat.springboot.courseswebsite.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UsersService usersService;
  private final TokenRepository tokenRepository;

  @Autowired
  private JwtService jwtService;

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

  @PostMapping("/checkToken")
  public ResponseEntity<Token> checkToken(@RequestBody Map<String,Object> payload) {
    String token = payload.get("token").toString();
    Optional<Token> resTkn = tokenRepository.findByToken(token);

    if(resTkn.isPresent()){
      Token tkn = resTkn.get();
      return ResponseEntity.ok(tkn);
    }
    throw new NotFoundException("token is not found");
  }

}
