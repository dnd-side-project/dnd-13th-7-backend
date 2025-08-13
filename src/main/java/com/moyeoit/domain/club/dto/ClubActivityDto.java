package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.ClubActivity;
import lombok.Builder;

@Builder
public record ClubActivityDto(
        String hashtag,
        String activityName,
        String activityDescribe,
        String imageUrl,
        Integer activityOrder) {

    public static ClubActivityDto from(ClubActivity entity) {
        return ClubActivityDto.builder()
                .hashtag(entity.getHashtag())
                .activityName(entity.getActivityName())
                .activityDescribe(entity.getActivityDescribe())
                .imageUrl(entity.getImageUrl())
                .activityOrder(entity.getActivityOrder())
                .build();
    }
}
