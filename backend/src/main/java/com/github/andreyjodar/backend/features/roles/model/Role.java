package com.github.andreyjodar.backend.features.roles.model;

import com.github.andreyjodar.backend.core.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

}
