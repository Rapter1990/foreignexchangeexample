package com.example.demo.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Abstract base class for entities that require a creation timestamp.
 * This class is designed to be extended by other entity classes that need to record
 * the date and time when the entity was created. The creation timestamp is automatically
 * set before persisting the entity to the database.
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    private LocalDateTime createdAt;

    /**
     * Callback method invoked before the entity is persisted to the database.
     * This method sets the {@link #createdAt} field to the current date and time.
     * It is called by the persistence provider before the entity is saved to ensure
     * that the creation timestamp is accurately recorded.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
