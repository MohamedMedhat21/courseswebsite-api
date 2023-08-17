package com.medhat.springboot.courseswebsite.auth;

import com.medhat.springboot.courseswebsite.dao.RolesRepository;
import com.medhat.springboot.courseswebsite.dao.UsersRepository;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.securingweb.JwtService;
import com.medhat.springboot.courseswebsite.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsersRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RolesRepository rolesRepository;

  public AuthenticationResponse register(RegisterRequest request) {
    Users user = new Users(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            Constants.DEFAULT_NEW_USER_ENABLED,
            rolesRepository.findByName(request.getRolename()).get()
    );

    repository.save(user);

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    Users user = repository.findByUserName(request.getUsername()).orElseThrow(RuntimeException::new);
    System.out.println(user);
    System.out.println("-0-----------------------------");

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
