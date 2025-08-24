package com.moyeoit.domain.club.controller.response;


import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import java.util.List;

public record ClubInfoResponse(
        ClubDto club,
        List<ClubActivityDto> activities,
        List<ClubScheduleDto> clubSchedules
) {
}
