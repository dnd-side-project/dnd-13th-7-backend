package com.moyeoit.domain.club;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.domain.club.dto.ClubPagingRequest;
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
        // 테스트 데이터 셋업
        Club club1 = createClub("백엔드 온라인 스터디", "백엔드", "온라인", List.of("Java"), List.of("대학생"), 2023);
        Club club2 = createClub("프론트엔드 오프라인 프로젝트", "프론트엔드", "오프라인", List.of("React"), List.of("취준생"), 2024);
        Club club3 = createClub("알고리즘 온라인 스터디", "백엔드", "온라인", List.of("Python", "Java"), List.of("누구나"), 2025);
        Club club4 = createClub("디자인 오프라인 프로젝트", "디자인", "오프라인", List.of("Figma"), List.of("직장인", "대학생"), 2022);

        clubRepository.saveAll(List.of(club1, club2, club3, club4));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("1. 필터 조건이 없을 때 전체 클럽을 최신순(ID)으로 페이징하여 조회한다")
    void findClub_noFilter() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).hasSize(2);
        // 기본 정렬은 ID 역순이므로, 가장 나중에 저장된 club3, club4가 나와야 함
        // (ID는 1, 2, 3, 4 순서로 생성된다고 가정)
        assertThat(result.getContent().get(0).getName()).isEqualTo("디자인 오프라인 프로젝트");
        assertThat(result.getContent().get(1).getName()).isEqualTo("알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("1-2. 정렬 조건(sort)이 있을 때 설립일순으로 페이징하여 조회한다")
    void findClub_withSort() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setSort("establishment"); // 정렬 조건 추가
        Pageable pageable = PageRequest.of(0, 2);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).hasSize(2);
        // 설립일(establishment) 역순으로 정렬되었는지 확인
        assertThat(result.getContent().get(0).getName()).isEqualTo("알고리즘 온라인 스터디"); // 2025년
        assertThat(result.getContent().get(1).getName()).isEqualTo("프론트엔드 오프라인 프로젝트"); // 2024년
    }

    @Test
    @DisplayName("2. 직무(field)로 필터링하여 조회한다")
    void findClub_byField() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("백엔드");
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("3. 진행 방식(way)으로 필터링하여 조회한다")
    void findClub_byWay() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setWay("온라인");
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("4. 기술 스택(part)으로 필터링하여 조회한다")
    void findClub_byPart() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setPart(List.of("Java"));
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Club::getName)
                .containsExactlyInAnyOrder("백엔드 온라인 스터디", "알고리즘 온라인 스터디");
    }

    @Test
    @DisplayName("5. 여러 조건을 복합하여 필터링한다")
    void findClub_byMultipleConditions() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("백엔드");
        request.setWay("온라인");
        request.setTarget(List.of("대학생"));
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        // '백엔드' + '온라인' + '대학생' 조건에 맞는 것은 '백엔드 온라인 스터디' 하나 뿐이다.
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("백엔드 온라인 스터디");
    }

    @Test
    @DisplayName("6. 조건에 맞는 결과가 없을 때 빈 페이지를 반환한다")
    void findClub_noResult() {
        // given
        ClubPagingRequest request = new ClubPagingRequest();
        request.setField("데브옵스");
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<Club> result = clubRepository.findClubByRequest(request, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }

    /**
     * 실제 엔티티 구조에 맞춰 테스트 데이터를 생성하는 헬퍼 메서드
     */
    private Club createClub(String name, String field, String way, List<String> parts, List<String> targets, Integer establishmentYear) {
        // 1. Club 엔티티 생성
        Club club = Club.builder()
                .name(name)
                .establishment(establishmentYear)
                .online("온라인".equals(way) ? "ZOOM" : null) // 온라인/오프라인 값 설정
                .offline("오프라인".equals(way) ? "강남역" : null)
                .position(new ArrayList<>()) // 리스트 초기화
                .target(new ArrayList<>())
                .build();

        // 2. Position 엔티티 생성 및 연관관계 설정
        Position position = new Position(null, field, club);
        club.getPosition().add(position);

        // 3. Target 엔티티 생성 및 연관관계 설정
        targets.forEach(targetName -> {
            Target target = new Target(null, targetName, club);
            club.getTarget().add(target);
        });

        // 4. ClubRecruitment 및 RecruitmentPart 엔티티 생성 및 연관관계 설정
        ClubRecruitment recruitment = ClubRecruitment.builder()
                .club(club)
                .recruitmentParts(new ArrayList<>())
                .build();

        parts.forEach(partName -> {
            RecruitmentPart part = new RecruitmentPart(null, partName, recruitment);
            recruitment.getRecruitmentParts().add(part);
        });

        // Club과 ClubRecruitment의 연관관계 설정 (양방향)
        club.setRecruitment(recruitment);

        return club;
    }

}
