package com.moyeoit.domain.review.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewPagingRequest {
    private String reviewType; //후기 종류
    private String clubId;
    private String part;//파트
    private String result;//결과 상태
    private String sort;//정렬
    private Boolean isRecruiting;//모집상태
}
