package com.moyeoit.context.club.presentation.response;


import com.moyeoit.context.club.application.dto.ClubActivityDto;
import com.moyeoit.context.club.application.dto.ClubDto;
import com.moyeoit.context.club.application.dto.ClubScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "동아리 상세 정보 응답")
public record ClubInfoResponse(
        @Schema(description = "동아리 기본 정보")
        ClubDto club,
        @Schema(description = "동아리 활동 정보 목록")
        List<ClubActivityDto> activities,
        @Schema(description = "동아리 활동 일정 목록")
        List<ClubScheduleDto> clubSchedules
) {
}
