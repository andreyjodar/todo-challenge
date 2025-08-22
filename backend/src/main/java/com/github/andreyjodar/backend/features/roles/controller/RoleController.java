package com.github.andreyjodar.backend.features.roles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<RoleResponse>> findAll(Pageable pageable) {
        Page<Role> roles = roleService.findAll(pageable);
        Page<RoleResponse> rolesResponse = roles.map(roleService::fromEntity);
        return ResponseEntity.ok(rolesResponse);
    }
    
}
