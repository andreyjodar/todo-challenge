package com.github.andreyjodar.backend.features.roles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.roles.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    public Optional<Role> findByName(String name);
}
