package com.github.andreyjodar.backend.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.features.users.model.LoginRequest;
import com.github.andreyjodar.backend.features.users.model.LoginResponse;
import com.github.andreyjodar.backend.features.users.model.User;
import com.github.andreyjodar.backend.features.users.model.UserResponse;
import com.github.andreyjodar.backend.features.users.service.UserService;
import com.github.andreyjodar.backend.core.security.JwtService;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User userDb = userService.findByEmail(loginRequest.getEmail());
            String token = jwtService.generateToken(authentication.getName());
            UserResponse userResponse = userService.fromEntity(userDb);
            LoginResponse loginResponse = new LoginResponse(token, userResponse);
        return loginResponse;
    }
}