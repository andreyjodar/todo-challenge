package com.github.andreyjodar.backend.features.roles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.model.RoleResponse;
import com.github.andreyjodar.backend.features.roles.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {
        List<Role> roles = roleService.findAll();
        List<RoleResponse> rolesResponse = roles.stream().map(roleService::fromEntity).toList();
        return ResponseEntity.ok(rolesResponse);
    }
}
