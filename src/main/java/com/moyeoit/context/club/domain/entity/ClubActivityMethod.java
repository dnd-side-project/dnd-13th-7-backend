package com.moyeoit.context.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ClubActivityMethod(
        @Column(name = "online")
        String online,

        @Column(name = "offline")
        String offline
) {}