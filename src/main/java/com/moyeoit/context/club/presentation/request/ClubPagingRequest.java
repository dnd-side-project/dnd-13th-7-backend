package com.moyeoit.context.club.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubPagingRequest {
    private String field;  // 분야
    private String part;   // 모집 파트
    private String way;    // 활동 방식
    private String target; // 모집 대상
    @Schema(
            description = "정렬 기준 (인기순: 구독 수 desc, 이름순: 동아리명 asc, 최신순: clubId desc). 기본값: 최신순",
            example = "인기순"
    )
    private String sort;   // 정렬 기준
}
