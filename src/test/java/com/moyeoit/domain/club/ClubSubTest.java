package com.moyeoit.domain.club;

import com.moyeoit.domain.club.entity.*;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.club.repository.ClubSubscribeRepository;
import com.moyeoit.domain.club.service.ClubService;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClubSubTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private UserRepository userRepository;

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

    public static User createAppUser() {
        Job job = createJob();
        return User.builder()
                .id(1L)
                .name("희태 박")
                .email("heetae@example.com")
                .nickname("heetae123")
                .provider(AuthProvider.GOOGLE)
                .active(true)
                .jobId(job.getId())
                .build();
    }

    public static Club createClub() {
        ClubProfile clubProfile = new ClubProfile(
                "함께 성장하는 개발 커뮤니티",
                "실무형 프로젝트와 스터디를 중심으로 활동하는 개발 동아리 입니다.",
                LocalDate.of(2025, 1, 1),
                50,
                1,
                "https://example.com/club-image.png"
        );

        ClubMethod clubMethod = new ClubMethod(
                "서울 강남구",
                "https://zoom.example.com"
        );

        ClubAddress clubAddress = new ClubAddress(
                "서울",
                "서울 강남구 테헤란로 123"
        );

        return Club.builder()
                .id(1L)
                .name("모여잇 개발 동아리")
                .clubProfile(clubProfile)
                .clubMethod(clubMethod)
                .clubAddress(clubAddress)
                .recruiting(true)
                .subscribeCount(10)
                .build();
    }

    @Test
    void Club_Subscribe_test() {
        User user = createAppUser();
        Club club = createClub();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
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
