package com.moyeoit.context.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record ClubProfile(

        @Column(name = "slogan")
        String slogan,               // 슬로건

        @Column(name = "bio")
        String bio,                  // 소개

        @Column(name = "establishment")
        LocalDate establishment,     // 설립일시

        @Column(name = "total_participant")
        Integer totalParticipant,    // 총 참여자

        @Column(name = "operation")
        Integer operation,           // 운영 기수

        @Column(name = "image_url")
        String imageUrl              // 이미지 URL
) {}
