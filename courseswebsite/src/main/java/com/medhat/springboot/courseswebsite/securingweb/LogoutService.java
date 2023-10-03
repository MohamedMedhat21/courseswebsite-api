package com.medhat.springboot.courseswebsite.securingweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medhat.springboot.courseswebsite.dao.TokenRepository;
import com.medhat.springboot.courseswebsite.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    Optional<Token> storedToken = tokenRepository.findByToken(jwt);


    if (storedToken.isPresent()) {
      Token jwtToken = storedToken.get();
      jwtToken.setExpired(true);
      jwtToken.setRevoked(true);
      tokenRepository.save(jwtToken);
    }
  }
}
