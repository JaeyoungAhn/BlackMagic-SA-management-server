package com.sms.blackmagic.controller;

import com.sms.blackmagic.model.User;
import com.sms.blackmagic.repository.UserRepository;
import com.sms.blackmagic.service.CustomUserDetailsService;
import com.sms.blackmagic.util.AuthenticationRequest;
import com.sms.blackmagic.util.AuthenticationResponse;
import com.sms.blackmagic.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getAccountName(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.out.println("incorrect");
            throw new Exception("Incorrect user name or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getAccountName());

        String accountName = authenticationRequest.getAccountName();
        User user = userRepository.findByAccountName(accountName);

        final String jwt = jwtUtil.generateToken(userDetails, user.getCompanyId());

        String username;
        username = jwtUtil.extractUsername(jwt);
        System.out.println(username);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println(username);

        return ResponseEntity.ok().build();
    }



}
