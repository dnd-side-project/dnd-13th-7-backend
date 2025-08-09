package com.moyeoit.domain.club.dto;

public record ClubDto(
        String name,
        String slogan,
        String bio,
        Integer establishment,
        Integer total_participant,
        Integer operation,
        String offline,
        String online,
        String location,
        String address,
        boolean recruiting,
        String imageUrl) {
}
