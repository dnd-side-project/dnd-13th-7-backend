package com.moyeoit.domain.club;

import com.moyeoit.domain.club.dto.ClubInfoResponse;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubSchedule;
import com.moyeoit.domain.club.repository.ClubRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Transactional
    public ClubInfoResponse findDetailInfo(Long clubId) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("찾을수 없는 동아리 입니다."));
        List<ClubActivity> activities = club.getActivities();
        List<ClubSchedule> schedules = club.getSchedules();

        return new ClubInfoResponse(clubMapper.toClubDto(club), clubMapper.toClubActivityDto(activities),
                clubMapper.toClubScheduleDto(schedules));
    }
}
