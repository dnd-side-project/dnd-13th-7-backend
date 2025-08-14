package com.moyeoit.domain.club.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long club_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @MapsId
    private Club club;

    @OneToMany(mappedBy = "clubRecruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentPart> recruitmentParts = new ArrayList<>();

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "recruitment_schedule")
    private String recruitmentSchedule;

    @Column(name = "activity_period")
    private String activityPeriod;

    @Column(name = "activity_method")
    private String activityMethod;

    @Column(name = "activity_fee")
    private String activityFee;

    @Column(name = "homepage_url")
    private String homepageUrl;

    @Column(name = "notice_url")
    private String noticeUrl;
}
