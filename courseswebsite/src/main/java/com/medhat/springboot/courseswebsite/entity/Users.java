package com.medhat.springboot.courseswebsite.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="enabled",nullable = false)
    private Integer enabled;

    @OneToOne()
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public Users(String userName, String password, String email, Integer enabled, Role role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority e = new SimpleGrantedAuthority(role.getName());

        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(e);

        return authList;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(this.enabled==1)
            return true;

        return false;
    }


}
