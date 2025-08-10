package com.moyeoit.domain.club;

import com.moyeoit.domain.club.dto.ClubActivityDto;
import com.moyeoit.domain.club.dto.ClubDto;
import com.moyeoit.domain.club.dto.ClubRecruitInfoResponse;
import com.moyeoit.domain.club.dto.ClubScheduleDto;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import com.moyeoit.domain.club.entity.ClubRecruitment;
import com.moyeoit.domain.club.entity.ClubSchedule;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    // Club 엔티티를 ClubDto로 변환
    ClubDto toClubDto(Club club);

    // ClubActivity 엔티티를 ClubActivityDto로 변환
    List<ClubActivityDto> toClubActivityDto(List<ClubActivity> clubActivity);

    // ClubSchedule 엔티티를 ClubScheduleDto로 변환
    List<ClubScheduleDto> toClubScheduleDto(List<ClubSchedule> clubSchedule);

    @Mapping(target = "recruitmentPart", source = "recruitmentPart", qualifiedByName = "stringToList")
    ClubRecruitInfoResponse toClubRecruitDto(ClubRecruitment clubRecruitment);

    @Named("stringToList")
    default List<String> stringToList(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.asList(value.split(","));
    }
}
