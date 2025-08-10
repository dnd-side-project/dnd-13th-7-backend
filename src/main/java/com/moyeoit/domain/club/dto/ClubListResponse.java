package com.moyeoit.domain.club.dto;

import java.util.List;

public record ClubListResponse(
        Long clubId,
        String clubName,
        String description,
        List<String> categories,
        String logoUrl,
        boolean isRecruiting) {
}
