package com.github.andreyjodar.backend.features.labels.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.labels.model.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
    
    public Optional<Label> findById(Long id);

    public Optional<Label> findByName(String name);
}
