package com.moyeoit.context.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ClubPlace(
        @Column(name = "location")
        String location,
        @Column(name = "address")
        String address
) {}
