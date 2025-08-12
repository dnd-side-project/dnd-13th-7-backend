package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.Position;
import java.util.List;

public record ClubListResponse(
        Long clubId,
        String clubName,
        String description,
        List<Position> categories,
        String logoUrl,
        boolean isRecruiting) {
    public static ClubListResponse from(Club club) {
        return new ClubListResponse(
                club.getId(),
                club.getName(),
                club.getBio(),
                club.getPosition(),
                club.getImage_url(),
                club.isRecruiting()
        );
    }
}
