package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.dto.ReviewQueryDto.PremiumReviewInfo;
import com.moyeoit.domain.review.repository.BasicReviewRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.domain.review.controller.response.BasicReviewListResponse;
import com.moyeoit.domain.review.controller.response.PremiumReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewListService {

    private final BasicReviewRepository basicReviewRepository;
    private final PremiumReviewRepository premiumReviewRepository;

    @Transactional(readOnly = true)
    public Page<BasicReviewListResponse> findBasicReviewList(ReviewPagingRequest request, Pageable pageable) {
        return basicReviewRepository.findBasicReviewByRequest(request, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PremiumReviewListResponse> findPremiumReviewList(ReviewPagingRequest request, Pageable pageable) {
        // 1. Repository를 호출하여 필요한 모든 데이터를 한 번에 조회
        Page<PremiumReviewInfo> reviewInfoPage = premiumReviewRepository.findPremiumReviewByRequest(request, pageable);
        return reviewInfoPage.map(PremiumReviewListResponse::from);
    }
}
