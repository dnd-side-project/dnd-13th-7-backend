package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.schedule.ClubSchedule;
import com.moyeoit.domain.club.entity.schedule.ClubSchedulePeriod;
import com.moyeoit.domain.club.entity.schedule.ClubSchedulePeriodType;
import lombok.Getter;

@Getter
public class ClubScheduleSaveRequest {
    private Long clubId;
    private Integer periodValue;
    private ClubSchedulePeriodType type;
    private String activity;

    public static ClubSchedule of(ClubScheduleSaveRequest request, Club club) {
        ClubSchedulePeriod clubSchedulePeriod = new ClubSchedulePeriod(
                request.getPeriodValue(),
                request.getType()
        );

        return ClubSchedule.builder()
                .club(club)
                .period(clubSchedulePeriod)
                .activity(request.getActivity())
                .build();
    }
}
