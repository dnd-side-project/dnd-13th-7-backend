package com.moyeoit.domain.club.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubProfile {

    @Column(name = "slogan")
    private String slogan;               // 슬로건

    @Column(name = "bio")
    private String bio;                  // 소개

    @Column(name = "establishment")
    private LocalDate establishment; // 설립일시

    @Column(name = "totalParticipant")
    private Integer totalParticipant;       // 총 참여자

    @Column(name = "operation")
    private Integer operation;           // 운영 기수

    @Column(name = "image_url")
    private String imageUrl;             // 이미지 URL

}