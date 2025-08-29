package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.dto.PremiumReviewDetailDto;
import com.moyeoit.domain.review.dto.ReviewQueryDto.PremiumReviewInfo;
import com.moyeoit.domain.review.repository.BasicReviewRepository;
import com.moyeoit.domain.review.repository.PremiumReviewDetailRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.domain.review.controller.response.BasicReviewListResponse;
import com.moyeoit.domain.review.controller.response.PremiumReviewListResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final PremiumReviewDetailRepository premiumReviewDetailRepository;


    @Transactional(readOnly = true)
    public Page<BasicReviewListResponse> findBasicReviewList(ReviewPagingRequest request, Pageable pageable) {
        return basicReviewRepository.findBasicReviewByRequest(request, pageable);
    }

    @Transactional(readOnly = true)
    public Page<PremiumReviewListResponse> findPremiumReviewList(ReviewPagingRequest request, Pageable pageable) {
        Page<PremiumReviewInfo> reviewInfoPage = premiumReviewRepository.findPremiumReviewByRequest(request, pageable);

        List<Long> reviewIds = reviewInfoPage.stream()
                .map(PremiumReviewInfo::getReviewId)
                .toList();

        List<PremiumReviewDetailDto> details = premiumReviewDetailRepository.findAllByReviewIdIn(reviewIds).stream().map(PremiumReviewDetailDto::new).toList();

        Map<Long, List<PremiumReviewDetailDto>> detailMap =
                premiumReviewDetailRepository.findAllByReviewIdIn(reviewIds).stream()
                        .map(PremiumReviewDetailDto::new)
                        .collect(Collectors.groupingBy(PremiumReviewDetailDto::getReviewId));


        return reviewInfoPage.map(info -> {
            List<PremiumReviewDetailDto> reviewDetails =
                    detailMap.getOrDefault(info.getReviewId(), List.of());
            return PremiumReviewListResponse.from(info, reviewDetails);
        });


    }
}
