package com.medhat.springboot.courseswebsite.securingweb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select users.username,role.name from users,role where users.role_id = role.id and users.username=?")
                .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors()  // enable cors filter
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/courses").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/**/users/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/**/users").hasAuthority("ADMIN")
                .antMatchers("/**/roles/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated().and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}