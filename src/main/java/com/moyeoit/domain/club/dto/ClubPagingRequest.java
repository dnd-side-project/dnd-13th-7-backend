package com.moyeoit.domain.club.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ClubPagingRequest {
    private String field;  // 분야
    private List<String> part;   // 모집 파트
    private String way;    // 활동 방식
    private List<String> target; // 모집 대상
    private String sort;   // 정렬 기준
}
