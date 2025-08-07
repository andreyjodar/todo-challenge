package com.github.andreyjodar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserService userService;
}
