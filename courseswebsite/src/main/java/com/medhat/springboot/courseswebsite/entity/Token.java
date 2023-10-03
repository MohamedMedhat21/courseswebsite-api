package com.medhat.springboot.courseswebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medhat.springboot.courseswebsite.entity.Users;
import lombok.*;

import javax.persistence.*;

@Data
@ToString(exclude = "user") // solving this ex: Handler dispatch failed; nested exception is java.lang.StackOverflowError
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Integer id;

  @Column(unique = true)
  private String token;

  private boolean revoked;

  private boolean expired;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;
}
