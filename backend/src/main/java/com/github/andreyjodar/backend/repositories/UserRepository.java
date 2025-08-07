package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
