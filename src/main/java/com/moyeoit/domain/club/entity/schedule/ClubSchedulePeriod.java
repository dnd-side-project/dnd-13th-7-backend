package com.moyeoit.domain.club.entity.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubSchedulePeriod {

    @Column(name = "period_value")
    private Integer periodValue;

    @Column(name = "period_type")
    private ClubSchedulePeriodType periodType;

}
