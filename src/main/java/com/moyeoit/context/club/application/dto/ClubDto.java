package com.moyeoit.context.club.application.dto;

import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.process.ClubProcess;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "동아리 기본 정보")
public record ClubDto(
        @Schema(description = "동아리 이름", example = "코딩의 민족")
        String name,
        @Schema(description = "동아리 슬로건", example = "배달하는 개발자들, 세상을 바꾼다!")
        String slogan,
        @Schema(description = "동아리 소개", example = "저희는 실무 중심의 프로젝트를 통해 함께 성장하는 IT 동아리입니다.")
        String bio,
        @Schema(description = "설립 연도", example = "2023")
        Integer establishment,
        @Schema(description = "총 인원", example = "50")
        Integer totalParticipant,
        @Schema(description = "운영 기간 (개월)", example = "12")
        Integer operation,
        @Schema(description = "오프라인 활동 정보", example = "주 1회 강남역 스터디룸")
        String offline,
        @Schema(description = "온라인 활동 정보", example = "Discord, Gather")
        String online,
        @Schema(description = "특이사항", example = "신입 부원 모집 중!")
        String significant,
        @Schema(description = "활동 지역", example = "서울")
        String location,
        @Schema(description = "상세 주소", example = "강남구 테헤란로")
        String address,
        @Schema(description = "모집 여부", example = "true")
        Boolean recruiting,
        @Schema(description = "동아리 대표 이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/club_image.jpg")
        String imageUrl,
        @Schema(description = "모집 절차", example = "[\"서류 접수\", \"코딩 테스트\", \"면접\", \"최종 합격\"]")
        List<String> process) {

    public static ClubDto from(Club entity) {
        return ClubDto.builder()
                .name(entity.getName())
                .slogan(entity.getClubProfile() .slogan())
                .bio(entity.getClubProfile().bio())
                .establishment(entity.getClubProfile().establishment().getYear())
                .totalParticipant(entity.getClubProfile().totalParticipant())
                .operation(entity.getClubProfile().operation())
                .offline(entity.getClubActivityMethod().offline())
                .online(entity.getClubActivityMethod().online())
                .significant(entity.getSignificant())
                .location(entity.getClubPlace().location())
                .address(entity.getClubPlace().address())
                .recruiting(entity.getRecruiting())
                .imageUrl(entity.getClubProfile().imageUrl())
                .process(entity.getProcesses().stream().map(ClubProcess::getDescription).toList())
                .build();
    }
}
