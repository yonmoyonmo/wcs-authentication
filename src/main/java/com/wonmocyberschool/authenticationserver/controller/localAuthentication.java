package com.wonmocyberschool.authenticationserver.controller;

import com.wonmocyberschool.authenticationserver.AuthProvider;
import com.wonmocyberschool.authenticationserver.DTO.ApiResponse;
import com.wonmocyberschool.authenticationserver.DTO.AuthResponse;
import com.wonmocyberschool.authenticationserver.DTO.LoginRequest;
import com.wonmocyberschool.authenticationserver.DTO.SignUpRequest;
import com.wonmocyberschool.authenticationserver.entity.User;
import com.wonmocyberschool.authenticationserver.exception.BadRequestException;
import com.wonmocyberschool.authenticationserver.jpaRepository.UserRepository;
import com.wonmocyberschool.authenticationserver.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class localAuthentication {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        System.out.println("local login occurred : " + loginRequest.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/signin")
                .build().toUri();

        System.out.println("local signup occurred : " + signUpRequest.getEmail());

        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        System.out.println("IP : " + clientIp);

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

}

