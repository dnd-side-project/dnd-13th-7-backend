package com.moyeoit.domain.club.controller.response;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.position.ClubPosition;
import java.util.List;

public record ClubListResponse(
        Long clubId,
        String clubName,
        String description,
        List<String> categories,
        String logoUrl,
        Boolean isRecruiting) {
    public static ClubListResponse from(Club club) {
        return new ClubListResponse(
                club.getId(),
                club.getName(),
                club.getClubProfile().getBio(),
                club.getPositions().stream().map(ClubPosition::getName).toList(),
                club.getClubProfile().getImageUrl(),
                club.getRecruiting()
        );
    }
}
