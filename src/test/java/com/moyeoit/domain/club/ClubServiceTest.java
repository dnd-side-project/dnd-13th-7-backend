package com.moyeoit.domain.club;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moyeoit.global.response.club.ClubInfoResponse;
import com.moyeoit.global.response.club.ClubRecruitInfoResponse;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.ClubSchedule;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.club.service.ClubService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private Club club;

    @BeforeEach
    void setUp() {

        // Club 빌더
        club = Club.builder()
                .id(1L)
                .name("테스트 동아리")
                .slogan("함께 성장하는 동아리")
                .bio("IT 중심 프로젝트 동아리")
                .establishment(2020)
                .totalParticipant(30)
                .operation(1)
                .offline("서울")
                .online("Zoom")
                .location("강남")
                .address("서울시 강남구")
                .recruiting(true)
                .imageUrl("test.png")
                .activities(new ArrayList<>())
                .schedules(new ArrayList<>())
                .process(new ArrayList<>())
                .target(new ArrayList<>())
                .target(new ArrayList<>())
                .build();

        // ClubActivity 빌더
        ClubActivity activity = ClubActivity.builder()
                .id(101L)
                .activityName("Spring Boot 프로젝트")
                .club(club)
                .build();

        // ClubSchedule 빌더
        ClubSchedule schedule = ClubSchedule.builder()
                .id(201L)
                .activity("매주 월요일 회의")
                .club(club)
                .build();

        club.getActivities().add(activity);
        club.getSchedules().add(schedule);

    }

    @Test
    void findDetailInfo() {
        // given
        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));

        // when
        ClubInfoResponse response = clubService.findDetailInfo(1L);

        // then
        assertThat(response.club().name()).isEqualTo("테스트 동아리");
        assertThat(response.activities()).hasSize(1);
        assertThat(response.clubSchedules()).hasSize(1);

        verify(clubRepository, times(1)).findById(1L);
    }

    @Test
    void findRecruitInfo() {
        //given
        Club club = mock(Club.class);
        ClubRecruitment recruitment =  new ClubRecruitment( 1L, // club_id
                club, // club
                new ArrayList<>(), // recruitmentParts
                "자격 요건 예시", // qualification
                "2025-09-01 ~ 2025-09-15", // recruitment_schedule
                "2025-09 ~ 2026-02", // activity_period
                "온라인", // activity_method
                "무료", // activity_fee
                "https://example.com", // homepage_url
                "https://example.com/notice");

        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
        when(club.getRecruitment()).thenReturn(recruitment);

        //when
        ClubRecruitInfoResponse response = clubService.findRecruitInfo(1L);

        //then
        assertEquals("자격 요건 예시",response.qualification());
        assertEquals("온라인",response.activityMethod());
        assertEquals("https://example.com/notice",response.noticeUrl());

        verify(clubRepository,times(1)).findById(1L);


    }
}