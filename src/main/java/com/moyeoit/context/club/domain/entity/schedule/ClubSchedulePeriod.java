package com.moyeoit.context.club.domain.entity.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private ClubSchedulePeriodType periodType;

}
