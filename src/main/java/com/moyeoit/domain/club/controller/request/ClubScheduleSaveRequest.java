package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubSchedule;
import lombok.Getter;

@Getter
public class ClubScheduleSaveRequest {
    private Long clubId;
    private Integer periodValue;
    private String period;
    private String activity;

    public static ClubSchedule of(ClubScheduleSaveRequest request,Club club) {
        return ClubSchedule.builder()
                .club(club)
                .periodValue(request.getPeriodValue())
                .period(request.getPeriod())
                .activity(request.getActivity())
                .build();
    }
}
