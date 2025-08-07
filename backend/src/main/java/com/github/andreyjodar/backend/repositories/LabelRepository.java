package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
    
}
