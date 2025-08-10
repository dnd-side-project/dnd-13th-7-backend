package com.moyeoit.domain.club.dto;


import java.util.List;

public record ClubInfoResponse(
        ClubDto club,
        List<ClubActivityDto> activities,
        List<ClubScheduleDto> clubSchedules
) {
}
