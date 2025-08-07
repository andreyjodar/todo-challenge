package com.github.andreyjodar.backend.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.andreyjodar.backend.enums.TaskPriority;
import com.github.andreyjodar.backend.enums.TaskStatus;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.tasktitle.notblank}")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "{validation.taskdescription.notblank}")
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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private List<TaskLabel> taskLabels = new ArrayList<>();

    @NotNull(message = "{validation.datetime.notnull}")
    @PastOrPresent(message = "{validation.datetime.notfuture}")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "{validation.datetime.notfuture}")
    private LocalDateTime deadline;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = TaskStatus.TODO;
        }
    }
}