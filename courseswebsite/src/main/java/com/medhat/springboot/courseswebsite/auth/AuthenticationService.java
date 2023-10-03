package com.medhat.springboot.courseswebsite.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhat.springboot.courseswebsite.dao.*;
import com.medhat.springboot.courseswebsite.entity.Token;
import com.medhat.springboot.courseswebsite.entity.Users;
import com.medhat.springboot.courseswebsite.securingweb.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UsersRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RolesRepository rolesRepository;
  private final TokenRepository tokenRepository;

  public AuthenticationResponse register(RegisterRequest request) {
    Users user = new Users(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            request.getEnabled(),
            rolesRepository.findByName(request.getRolename()).get()
    );

    repository.save(user);

    String jwtToken = jwtService.generateToken(user);

    saveUserToken(user, jwtToken);
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
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(Users user, String jwtToken) {
    Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Users user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

}
