package com.github.andreyjodar.backend.features.labels.model;

import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "labels")
public class Label extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

}