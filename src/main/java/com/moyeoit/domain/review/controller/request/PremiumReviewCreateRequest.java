package com.moyeoit.domain.review.controller.request;

import com.moyeoit.domain.review.controller.request.answer.AnswerRequest;
import com.moyeoit.domain.review.domain.ResultType;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PremiumReviewCreateRequest {

    private ReviewType reviewType; // 일반/프리미엄
    private ReviewCategory reviewCategory; // 서류/면접/활동

    private Long clubId; // 동아리 ID
    private Integer generation; // 기수
    private Long jobId; // 직군 ID

    private String imageUrl; // 이미지 URL
    private String title; // 리뷰 제목

    private List<AnswerRequest> questions;
    private ResultType resultType;

}
