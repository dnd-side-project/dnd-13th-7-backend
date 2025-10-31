package com.moyeoit.domain.club.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMethod {

    @Column(name = "online")
    private String online;               // 온라인 활동 방식

    @Column(name = "offline")
    private String offline;              // 오프라인 활동 방식

}