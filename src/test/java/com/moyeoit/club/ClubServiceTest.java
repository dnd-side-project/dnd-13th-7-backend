package com.moyeoit.club;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.moyeoit.domain.club.ClubMapper;
import com.moyeoit.domain.club.ClubService;
import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.dto.ClubInfoResponse;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubSchedule;
import com.moyeoit.domain.club.repository.ClubRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ClubMapper clubMapper;

    @InjectMocks
    private ClubService clubService;

    @Test
    void 동아리상세정보찾기_상세정보가있을때() {

        // given
        Long clubId = 1L;

        Club club = mock(Club.class);
        List<ClubActivity> activities = List.of(mock(ClubActivity.class));
        List<ClubSchedule> schedules = List.of(mock(ClubSchedule.class));

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));
        when(club.getActivities()).thenReturn(activities);
        when(club.getSchedules()).thenReturn(schedules);

        ClubDto clubDto = mock(ClubDto.class);
        List<ClubActivityDto> activityDtos = List.of(mock(ClubActivityDto.class));
        List<ClubScheduleDto> scheduleDtos = List.of(mock(ClubScheduleDto.class));

        when(clubMapper.toClubDto(club)).thenReturn(clubDto);
        when(clubMapper.toClubActivityDto(activities)).thenReturn(activityDtos);
        when(clubMapper.toClubScheduleDto(schedules)).thenReturn(scheduleDtos);

        // when
        ClubInfoResponse response = clubService.findDetailInfo(clubId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.club()).isEqualTo(clubDto);
        assertThat(response.activities()).isEqualTo(activityDtos);
        assertThat(response.clubSchedules()).isEqualTo(scheduleDtos);

        verify(clubRepository).findById(clubId);
        verify(clubMapper).toClubDto(club);
        verify(clubMapper).toClubActivityDto(activities);
        verify(clubMapper).toClubScheduleDto(schedules);
        verifyNoMoreInteractions(clubRepository, clubMapper);
    }

}