package com.moyeoit.domain.club.service;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.club.controller.response.ClubFindListResponse;
import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.controller.response.ClubInfoResponse;
import com.moyeoit.domain.club.controller.response.ClubListResponse;
import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.domain.club.controller.response.ClubRecruitInfoResponse;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubKeyword;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.ClubSchedule;
import com.moyeoit.domain.club.entity.ClubSubscribe;
import com.moyeoit.domain.club.entity.Club_ClubKeyword;
import com.moyeoit.domain.club.repository.ClubKeywordRepository;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.club.repository.ClubSubscribeRepository;
import com.moyeoit.domain.review.domain.ReviewLike;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubKeywordRepository keywordRepository;
    private final AppUserRepository userRepository;
    private final ClubSubscribeRepository clubSubscribeRepository;

    @Transactional(readOnly = true)
    public ClubInfoResponse findDetailInfo(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        List<ClubActivity> activities = club.getActivities();
        List<ClubSchedule> schedules = club.getSchedules();

        return new ClubInfoResponse(
                ClubDto.from(club),
                activities.stream().map(ClubActivityDto::from).toList(),
                schedules.stream().map(ClubScheduleDto::from).toList());
    }

    @Transactional(readOnly = true)
    public ClubRecruitInfoResponse findRecruitInfo(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));

        ClubRecruitment recruitment = club.getRecruitment();
        return ClubRecruitInfoResponse.from(recruitment);
    }

    @Transactional(readOnly = true)
    public Page<ClubListResponse> findClubList(ClubPagingRequest request, Pageable pageable) {
        return clubRepository.findClubByRequest(request, pageable).map(ClubListResponse::from);
    }

    @Transactional(readOnly = true)
    public List<ClubFindListResponse> searchClubList(String keyword){
        ClubKeyword clubKeyword =  keywordRepository.findClubKeywordByContent(keyword).orElseThrow(()-> new AppException(ClubErrorCode.NOT_FOUND_KEYWORD));
        return clubKeyword.getClubs().stream().map(Club_ClubKeyword::getClub).map(ClubFindListResponse::from).toList();
    }

    @Transactional
    public boolean subscribeClub(Long clubId,Long userId){
        Club club = clubRepository.findById(clubId).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        AppUser user = userRepository.findById(userId).orElseThrow(()->new AppException(UserErrorCode.NOT_FOUND));

        Optional<ClubSubscribe> existingSubscribe = clubSubscribeRepository.findByUserAndClub(user,club);
        boolean subscribed = existingSubscribe
                .map(subscribe -> {
                    clubSubscribeRepository.delete(subscribe);
                    return false;
                })
                .orElseGet(() -> {
                    clubSubscribeRepository.save(
                            ClubSubscribe.builder()
                                    .user(user)
                                    .club(club)
                                    .build());
                    return true;
                });

        return subscribed;
    }
}
