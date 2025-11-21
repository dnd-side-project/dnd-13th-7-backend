package com.moyeoit.domain.club.controller.response;

import com.moyeoit.domain.club.entity.Club;

public record ClubFindListResponse(
        Long clubId,
        String name,
        String imgUrl
) {
    public static ClubFindListResponse from(Club club) {
        return new ClubFindListResponse(
                club.getId(),
                club.getName(),
                club.getClubProfile().getImageUrl()
        );
    }
}
