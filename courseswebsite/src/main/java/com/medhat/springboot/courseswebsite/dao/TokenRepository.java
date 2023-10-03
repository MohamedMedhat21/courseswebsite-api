package com.medhat.springboot.courseswebsite.dao;

import java.util.List;
import java.util.Optional;

import com.medhat.springboot.courseswebsite.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = " select t from Token t " +
          "inner join Users u on t.user.id = u.id " +
          "where u.id = :id and (t.expired = false or t.revoked = false) ")
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
}
