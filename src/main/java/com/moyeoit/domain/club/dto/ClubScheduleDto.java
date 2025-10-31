package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.schedule.ClubSchedule;
import lombok.Builder;

@Builder
public record ClubScheduleDto(
        Integer periodValue,
        String period,
        String activity) {

    public static ClubScheduleDto from(ClubSchedule entity) {
        return ClubScheduleDto.builder()
                .periodValue(entity.getPeriod().getPeriodValue())
                .period(entity.getPeriod().getPeriodType().getLabel())
                .activity(entity.getActivity())
                .build();
    }
}
