package com.moyeoit.domain.club;

import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.dto.ClubInfoResponse;
import com.moyeoit.domain.club.dto.ClubListResponse;
import com.moyeoit.domain.club.dto.ClubPagingRequest;
import com.moyeoit.domain.club.dto.ClubRecruitInfoResponse;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.ClubSchedule;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

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

    public Page<ClubListResponse> findClubList(ClubPagingRequest request, Pageable pageable) {
        return clubRepository.findClubByRequest(request, pageable).map(ClubListResponse::from);
    }
}
