package com.moyeoit.domain.club.dto;

public record ClubActivityDto(
        String hashtag,
        String activityName,
        String activityDescribe,
        String imageUrl,
        Integer activityOrder) {
}
