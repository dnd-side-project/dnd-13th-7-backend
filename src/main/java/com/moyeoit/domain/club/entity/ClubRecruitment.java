package com.moyeoit.domain.club.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClubRecruitment {

    @Id
    @Column(name = "club_id")
    private Long club_id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "recruitment_part")
    private String recruitment_part;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "recruitment_schedule")
    private String recruitment_schedule;

    @Column(name = "activity_period")
    private String activity_period;

    @Column(name = "activity_method")
    private String activity_method;

    @Column(name = "activity_fee")
    private String activity_fee;

    @Column(name = "homepage_url")
    private String homepage_url;

    @Column(name = "notice_url")
    private String notice_url;
}
