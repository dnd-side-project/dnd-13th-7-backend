package com.moyeoit.context.club.domain.entity;


import com.moyeoit.context.club.domain.entity.activity.ClubActivity;
import com.moyeoit.context.club.domain.entity.position.ClubPosition;
import com.moyeoit.context.club.domain.entity.process.ClubProcess;
import com.moyeoit.context.club.domain.entity.schedule.ClubSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "tb_club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    private ClubProfile clubProfile;

    @Embedded
    private ClubMethod clubMethod;

    @Embedded
    private ClubAddress clubAddress;

    @Column(name = "significant")
    private String significant;

    @Column(name = "recruiting")
    private Boolean recruiting;

    @Column(name = "subscribe_count")
    private Integer subscribeCount;

    @OneToOne(mappedBy = "club", fetch = FetchType.LAZY)
    private ClubRecruitment recruitment;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private List<ClubActivity> activities;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private List<ClubSchedule> schedules;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private List<ClubProcess> processes;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    private List<ClubPosition> positions;

}