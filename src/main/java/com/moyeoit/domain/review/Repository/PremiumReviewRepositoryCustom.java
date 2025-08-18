package com.moyeoit.domain.review.Repository;



import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.dto.ReviewQueryDto.PremiumReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewRepositoryCustom {
    Page<PremiumReviewInfo> findPremiumReviewByRequest(ReviewPagingRequest request, Pageable pageable);
}
