package com.github.andreyjodar.backend.features.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.github.andreyjodar.backend.core.models.BaseEntity;
import com.github.andreyjodar.backend.features.labels.model.Label;
import com.github.andreyjodar.backend.features.users.model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @NotNull @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tasks_labels",
        joinColumns = @JoinColumn(name = "id_task"),
        inverseJoinColumns = @JoinColumn(name = "id_label")
    )
    private Set<Label> labels = new HashSet<>();

    @NotNull @Future
    @Column(nullable = false)
    private LocalDate deadline;

    @NotNull 
    @Column(name = "end_date", nullable = true)
    private LocalDateTime endDate;
}