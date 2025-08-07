package com.github.andreyjodar.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Data
@Table(name = "label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.labelname.notblank}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{validation.labelauthor.notnull}")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @NotNull(message = "{validation.datetime.notnull}")
    @PastOrPresent(message = "{validation.datetime.notfuture}")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "{validation.datetime.notfuture}")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}