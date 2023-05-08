package com.sms.blackmagic.config;

import com.sms.blackmagic.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable();
        return http.build();

//        http.authorizeHttpRequests(authorizeRequests ->
//                {
//                    try {
//                        authorizeRequests
//                                .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//        http.csrf().disable();
//        http.httpBasic().disable();
//        return http.build();
    }


}

