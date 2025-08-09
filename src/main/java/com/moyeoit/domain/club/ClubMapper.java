package com.moyeoit.domain.club;

import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubSchedule;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    // Club 엔티티를 ClubDto로 변환
    ClubDto toClubDto(Club club);

    // ClubActivity 엔티티를 ClubActivityDto로 변환
    List<ClubActivityDto> toClubActivityDto(List<ClubActivity> clubActivity);

    // ClubSchedule 엔티티를 ClubScheduleDto로 변환
    List<ClubScheduleDto> toClubScheduleDto(List<ClubSchedule> clubSchedule);
}
