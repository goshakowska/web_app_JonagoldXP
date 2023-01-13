package com.jonagoldxp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jonagoldxp.security.oauth.OAuth2LoginSuccessHandler;
import com.jonagoldxp.security.oauth.CustomerOAuth2UserService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DatabaseLoginSuccessHandler databaseLoginHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //defines which URL paths should be secured ad which not.
        http.authorizeHttpRequests()
                        .requestMatchers("/", "/home").authenticated()
                        .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .successHandler(databaseLoginHandler)
                    .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oauth2LoginHandler)
                .logout().permitAll()
                .and()
                        .rememberMe()
                        .key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
                        .tokenValiditySeconds(14 * 24 * 60 * 60)
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerUserDetailsService();
    }
}