package com.moyeoit.domain.club;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.AuthProvider;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubSubscribe;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.club.repository.ClubSubscribeRepository;
import com.moyeoit.domain.club.service.ClubService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClubSubTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private ClubSubscribeRepository clubSubscribeRepository;

    @InjectMocks
    private ClubService clubService;

    public static Job createJob() {
        return Job.builder()
                .id(1L)
                .name("Backend Developer")
                .build();
    }

    public static AppUser createAppUser() {
        Job job = createJob();
        return AppUser.builder()
                .id(1L)
                .name("희태 박")
                .email("heetae@example.com")
                .nickname("heetae123")
                .provider(AuthProvider.GOOGLE)
                .active(true)
                .job(job)
                .build();
    }

    public static Club createClub() {
        return Club.builder()
                .id(1L)
                .name("모여잇 개발 동아리")
                .slogan("함께 성장하는 개발 커뮤니티")
                .bio("실무형 프로젝트와 스터디를 중심으로 활동하는 개발 동아리입니다.")
                .establishment(2015)
                .totalParticipant(50)
                .operation(1)
                .offline("서울 강남구")
                .online("https://zoom.example.com")
                .location("서울")
                .address("서울 강남구 테헤란로 123")
                .recruiting(true)
                .imageUrl("https://example.com/club-image.png")
                .subscribeCount(10)
                .build();
    }

    @Test
    void Club_Subscribe_test() {
        AppUser user = createAppUser();
        Club club = createClub();

        when(appUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(clubRepository.findById(club.getId())).thenReturn(Optional.of(club));

        when(clubSubscribeRepository.findByUserAndClub(user, club)).thenReturn(Optional.empty());


        boolean subscribed = clubService.subscribeClub(user.getId(), club.getId());
        assertTrue(subscribed);


        when(clubSubscribeRepository.findByUserAndClub(user, club))
                .thenReturn(Optional.of(ClubSubscribe.builder()
                        .user(user)
                        .club(club)
                        .build()));

        boolean unsubscribed = clubService.subscribeClub(user.getId(), club.getId());
        assertFalse(unsubscribed);
    }
}
