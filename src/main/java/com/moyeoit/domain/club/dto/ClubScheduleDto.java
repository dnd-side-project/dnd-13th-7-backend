package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.ClubSchedule;
import lombok.Builder;

@Builder
public record ClubScheduleDto(
        Integer periodValue,
        String period,
        String activity) {

    public static ClubScheduleDto from(ClubSchedule entity) {
        return ClubScheduleDto.builder()
                .periodValue(entity.getPeriod_value())
                .period(entity.getPeriod())
                .activity(entity.getActivity())
                .build();
    }
}
