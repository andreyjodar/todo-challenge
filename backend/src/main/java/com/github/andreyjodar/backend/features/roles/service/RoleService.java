package com.github.andreyjodar.backend.features.roles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.model.RoleResponse;
import com.github.andreyjodar.backend.features.roles.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public RoleResponse fromEntity(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setCreatedAt(role.getCreatedAt());
        return roleResponse;
    }
}
