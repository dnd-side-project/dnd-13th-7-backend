package com.moyeoit.global.response.club;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.Position;
import java.util.List;

public record ClubListResponse(
        Long clubId,
        String clubName,
        String description,
        List<String> categories,
        String logoUrl,
        boolean isRecruiting) {
    public static ClubListResponse from(Club club) {
        return new ClubListResponse(
                club.getId(),
                club.getName(),
                club.getBio(),
                club.getPosition().stream().map(Position::getPositionName).toList(),
                club.getImageUrl(),
                club.isRecruiting()
        );
    }
}
