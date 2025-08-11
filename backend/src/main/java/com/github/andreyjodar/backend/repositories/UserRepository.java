package com.github.andreyjodar.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.andreyjodar.backend.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT FROM User WHERE email=:email")
    public Page<User> findByEmail(@Param("email") String email, Pageable pageable);

    public Optional<User> findByEmail(String email);
}
