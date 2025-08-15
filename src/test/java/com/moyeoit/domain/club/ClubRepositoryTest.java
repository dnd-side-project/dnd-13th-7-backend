package com.moyeoit.domain.club;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.Position;
import com.moyeoit.domain.club.entity.RecruitmentPart;
import com.moyeoit.domain.club.entity.Target;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.global.config.QueryDslConfig;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClubRepositoryTest {


    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        Job javaJob = Job.builder().name("Java").engName("Java").build();
        Job reactJob = Job.builder().name("React").engName("React").build();
        Job pythonJob = Job.builder().name("Python").engName("Python").build();
        Job figmaJob = Job.builder().name("Figma").engName("Figma").build();
        em.persist(javaJob);
        em.persist(reactJob);
        em.persist(pythonJob);
        em.persist(figmaJob);

        Club club1 = createClub("백엔드 온라인 스터디", "백엔드", "온라인", List.of(javaJob), List.of("대학생"), 2023);
        Club club2 = createClub("프론트엔드 오프라인 프로젝트", "프론트엔드", "오프라인", List.of(reactJob), List.of("취준생"), 2024);
        Club club3 = createClub("알고리즘 온라인 스터디", "백엔드", "온라인", List.of(pythonJob, javaJob), List.of("누구나"), 2025);
        Club club4 = createClub("디자인 오프라인 프로젝트", "디자인", "오프라인", List.of(figmaJob), List.of("직장인", "대학생"), 2022);

        clubRepository.saveAll(List.of(club1, club2, club3, club4));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("1. 필터 조건이 없을 때 전체 클럽을 최신순(ID)으로 페이징하여 조회한다")
    void findClub_noFilter() {
        ClubPagingRequest request = new ClubPagingRequest();
        Pageable pageable = PageRequest.of(0, 2);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("디자인 오프라인 프로젝트");
        assertThat(result.getContent().get(1).getName()).isEqualTo("알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("1-2. 정렬 조건(sort)이 있을 때 설립일순으로 페이징하여 조회한다")
    void findClub_withSort() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setSort("establishment");
        Pageable pageable = PageRequest.of(0, 2);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("알고리즘 온라인 스터디");
        assertThat(result.getContent().get(1).getName()).isEqualTo("프론트엔드 오프라인 프로젝트");
    }

    @Test
    @DisplayName("2. 직무(field)로 필터링하여 조회한다")
    void findClub_byField() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("백엔드");
        Pageable pageable = PageRequest.of(0, 5);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("3. 진행 방식(way)으로 필터링하여 조회한다")
    void findClub_byWay() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setWay("온라인");
        Pageable pageable = PageRequest.of(0, 5);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("4. 모집 분야(part) 이름으로 필터링하여 조회한다")
    void findClub_byPart() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setPart(List.of("Java"));
        Pageable pageable = PageRequest.of(0, 5);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("5. 여러 조건을 복합하여 필터링한다")
    void findClub_byMultipleConditions() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("백엔드");
        request.setWay("온라인");
        request.setTarget(List.of("대학생"));
        Pageable pageable = PageRequest.of(0, 5);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("백엔드 온라인 스터디");
    }

    @Test
    @DisplayName("6. 조건에 맞는 결과가 없을 때 빈 페이지를 반환한다")
    void findClub_noResult() {
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("데브옵스");
        Pageable pageable = PageRequest.of(0, 5);

        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }

    private Club createClub(String name, String field, String way, List<Job> jobs, List<String> targets, Integer establishmentYear) {
        Club club = Club.builder()
                .name(name)
                .establishment(establishmentYear)
                .online("온라인".equals(way) ? "ZOOM" : null)
                .offline("오프라인".equals(way) ? "강남역" : null)
                .position(new ArrayList<>())
                .target(new ArrayList<>())
                .build();

        Position position = new Position(null, field, club);
        club.getPosition().add(position);

        targets.forEach(targetName -> {
            Target target = new Target(null, targetName, club);
            club.getTarget().add(target);
        });

        ClubRecruitment recruitment = ClubRecruitment.builder()
                .club(club)
                .recruitmentParts(new ArrayList<>())
                .build();

        jobs.forEach(job -> {
            RecruitmentPart part = new RecruitmentPart(null, job,recruitment);
            recruitment.getRecruitmentParts().add(part);
        });

        club.setRecruitment(recruitment);
        return club;
    }
}