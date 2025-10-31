package com.moyeoit;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubAddress;
import com.moyeoit.domain.club.entity.ClubMethod;
import com.moyeoit.domain.club.entity.ClubProfile;
import com.moyeoit.domain.club.entity.activity.ClubActivity;
import com.moyeoit.domain.club.repository.ClubActivityRepository;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.domain.user.repository.JobRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootApplication
public class MoyeoitApiApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubActivityRepository clubActivityRepository;

	public static void main(String[] args) {
		SpringApplication.run(MoyeoitApiApplication.class, args);
	}


    @PostConstruct
    @Transactional
    public void initData() {
        Job job = Job.builder()
                .name("개발자")
                .engName("develoepr")
                .build();

        Job saveJob = jobRepository.save(job);

        User user = User.builder()
                .name("진승범")
                .email("jinsb1999@naver.com")
                .nickname("jin-daram")
                .provider(AuthProvider.GOOGLE)
                .active(true)
                .jobId(saveJob.getId())
                .deleted(false)
                .build();

        userRepository.save(user);

        // 동아리 데이터 생성
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

        Club club = Club.builder()
            .name("모여잇 개발 동아리")
            .clubProfile(clubProfile)
            .clubMethod(clubMethod)
            .clubAddress(clubAddress)
            .recruiting(true)
            .subscribeCount(10)
            .build();

        clubRepository.save(club);

        ClubActivity firstClubActivity = ClubActivity.builder()
                .hashtag("HASHTAG")
                .name("활동 1")
                .club(club)
                .description("활동 1 설명")
                .sequence(1)
                .build();

        ClubActivity secondClubActivity = ClubActivity.builder()
                .hashtag("HASHTAG")
                .name("활동 2")
                .club(club)
                .description("활동 2 설명")
                .sequence(2)
                .build();

        clubActivityRepository.save(firstClubActivity);
        clubActivityRepository.save(secondClubActivity);
    }

}
