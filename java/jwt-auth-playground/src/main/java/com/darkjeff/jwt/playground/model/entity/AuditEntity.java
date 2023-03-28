package com.darkjeff.jwt.playground.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AuditEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    private void setAuditCreated() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = "spring";
    }

    @PreUpdate
    private void setAuditUpdated() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "spring";
    }
}
