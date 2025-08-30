package com.moyeoit.domain.club.controller.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubPagingRequest {
    private String field;  // 분야
    private String part;   // 모집 파트
    private String way;    // 활동 방식
    private String target; // 모집 대상
    private String sort;   // 정렬 기준
}
