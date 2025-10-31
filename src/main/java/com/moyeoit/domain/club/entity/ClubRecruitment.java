package com.moyeoit.domain.club.entity;

import jakarta.persistence.*;

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
@Table(name = "tb_club_recruitment")
public class ClubRecruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_recruitment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "clubRecruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubRecruitmentPart> clubRecruitmentParts = new ArrayList<>();

    @Column(name = "activity_period")
    private String activityPeriod;

    @Column(name = "recruitment_schedule")
    private String recruitmentSchedule; // TODO : AttributeConverter 사용하기

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "activity_method")
    private String activityMethod;

    @Column(name = "activity_fee")
    private Long activityFee;

    @Column(name = "homepage_url")
    private String homepageUrl;

    @Column(name = "notice_url")
    private String noticeUrl;

}
