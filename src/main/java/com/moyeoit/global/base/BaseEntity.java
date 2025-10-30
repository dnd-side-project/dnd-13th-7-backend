package com.moyeoit.global.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false, updatable = false, name = "updated_date")
    private LocalDateTime updateDate;

}