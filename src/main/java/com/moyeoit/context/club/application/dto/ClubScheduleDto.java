package com.moyeoit.context.club.application.dto;

import com.moyeoit.context.club.domain.entity.schedule.ClubSchedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "동아리 활동 일정")
public record ClubScheduleDto(
        @Schema(description = "주기 값", example = "1")
        Integer periodValue,
        @Schema(description = "주기 단위", example = "주")
        String period,
        @Schema(description = "활동 내용", example = "정기 스터디")
        String activity) {

    public static ClubScheduleDto from(ClubSchedule entity) {
        return ClubScheduleDto.builder()
                .periodValue(entity.getPeriod().getPeriodValue())
                .period(entity.getPeriod().getPeriodType().getLabel())
                .activity(entity.getActivity())
                .build();
    }
}
