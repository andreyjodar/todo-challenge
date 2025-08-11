package com.github.andreyjodar.backend.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.andreyjodar.backend.models.enums.TaskPriority;
import com.github.andreyjodar.backend.models.enums.TaskStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "task")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotBlank(message = "{validation.tasktitle.notblank}")
    @Column(nullable = false)
    @ToString.Include
    private String title;

    @Column(nullable = false)
    private String description;

    @NotNull(message = "{validation.taskpriority.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @NotNull(message = "{validation.taskstatus.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "{validation.taskauthor.notnull}")
    private User author;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<TaskLabel> taskLabels = new ArrayList<>();

    @NotNull(message = "{validation.datetime.notnull}")
    @Column(nullable = false)
    private LocalDateTime deadline;
}