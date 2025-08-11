package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.Club;
import java.util.List;

public record ClubListResponse(
        Long clubId,
        String clubName,
        String description,
        List<String> categories,
        String logoUrl,
        boolean isRecruiting) {
    public static ClubListResponse from(Club club) {
        List<String> generatedCategories = List.of(club.getPosition().split(","));
        return new ClubListResponse(
                club.getId(),
                club.getName(),
                club.getBio(),
                generatedCategories,
                club.getImage_url(),
                club.isRecruiting()
        );
    }
}
