package com.moyeoit.context.club.service;

import com.moyeoit.context.club.controller.request.ClubPagingRequest;
import com.moyeoit.context.club.controller.response.ClubFindListResponse;
import com.moyeoit.context.club.controller.response.ClubInfoResponse;
import com.moyeoit.context.club.controller.response.ClubListResponse;
import com.moyeoit.context.club.controller.response.ClubRecruitInfoResponse;
import com.moyeoit.context.club.dto.ClubActivityDto;
import com.moyeoit.context.club.dto.ClubDto;
import com.moyeoit.context.club.dto.ClubScheduleDto;
import com.moyeoit.context.club.entity.Club;
import com.moyeoit.context.club.entity.ClubRecruitment;
import com.moyeoit.context.club.entity.ClubSubscribe;
import com.moyeoit.context.club.entity.activity.ClubActivity;
import com.moyeoit.context.club.entity.schedule.ClubSchedule;
import com.moyeoit.context.club.repository.ClubRepository;
import com.moyeoit.context.club.repository.ClubSubscribeRepository;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubSubscribeRepository clubSubscribeRepository;

    /**
     * 동아리 프로필/활동/일정 정보를 조회합니다.
     */
    @Transactional(readOnly = true)
    public ClubInfoResponse findDetailInfo(Long clubId) {
        Club club = clubRepository.findClubWithActivitiesById(clubId)
                .orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));

        List<ClubActivity> activities = club.getActivities();
        List<ClubSchedule> schedules = club.getSchedules();

        return new ClubInfoResponse(
                ClubDto.from(club),
                activities.stream().map(ClubActivityDto::from).toList(),
                schedules.stream().map(ClubScheduleDto::from).toList());
    }


    /**
     * 동아리 프로필/공고 정보를 조회합니다.
     */
    @Transactional(readOnly = true)
    public ClubRecruitInfoResponse findRecruitInfo(Long clubId) {
        Club club = clubRepository.findClubWithRecruitmentById(clubId)
                .orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));

        ClubRecruitment recruitment = club.getRecruitment();
        return ClubRecruitInfoResponse.from(recruitment);
    }


    @Transactional(readOnly = true)
    public Page<ClubListResponse> findClubList(ClubPagingRequest request, Pageable pageable) {
        return clubRepository.findClubByRequest(request, pageable).map(ClubListResponse::from);
    }

    @Transactional(readOnly = true)
    public List<ClubFindListResponse> suggestClubList(String keyword) {
        return clubRepository.findByNameContaining(keyword).stream().map(ClubFindListResponse::from).toList();
    }

    @Transactional
    public boolean subscribeClub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        Optional<ClubSubscribe> existingSubscribe = clubSubscribeRepository.findByUserAndClub(user, club);

        return existingSubscribe
                .map(subscribe -> {
                    clubSubscribeRepository.delete(subscribe);
                    clubRepository.minusSubCount(clubId);
                    return false;
                })
                .orElseGet(() -> {
                    clubSubscribeRepository.save(
                            ClubSubscribe.builder()
                                    .user(user)
                                    .club(club)
                                    .build());
                    clubRepository.plusSubCount(clubId);
                    return true;
                });
    }

    @Transactional(readOnly = true)
    public Page<ClubListResponse> subClubList(Long userId, Pageable pageable) {
        return clubRepository.findSubscribedClubs(userId, pageable).map(ClubListResponse::from);
    }

    @Transactional(readOnly = true)
    public boolean findOutClubSub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
        return clubSubscribeRepository.existsByClubAndUser(club, user);
    }

    public Page<ClubListResponse> searchClubList(String keyword,Pageable pageable) {
        List<ClubListResponse> results = clubRepository.findByNameContaining(keyword)
                .stream()
                .map(ClubListResponse::from)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), results.size());

        return new PageImpl<>(results.subList(start, end), pageable, results.size());
    }
}
