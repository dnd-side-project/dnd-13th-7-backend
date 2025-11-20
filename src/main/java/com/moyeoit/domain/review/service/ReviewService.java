package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.response.v2.OriginalReviewDetailView;
import com.moyeoit.domain.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.domain.review.domain.model.Review;
import com.moyeoit.domain.review.domain.model.ReviewAnswer;
import com.moyeoit.domain.review.domain.service.ReviewAnswerConverter;
import com.moyeoit.domain.review.infra.QueryReviewRepository;
import com.moyeoit.domain.review.infra.ReviewAnswerRepository;
import com.moyeoit.domain.review.infra.ReviewRepository;
import com.moyeoit.domain.review.infra.generator.ReviewAnswerGenerator;
import com.moyeoit.domain.review.presentation.request.ReviewCreateRequest;
import com.moyeoit.domain.review.presentation.request.ReviewSearchRequest;
import com.moyeoit.domain.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.domain.review.presentation.response.ReviewView;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ReviewAnswerConverter reviewAnswerConverter;
    private final ReviewAnswerGenerator reviewAnswerGenerator;
    private final QueryReviewRepository queryReviewRepository;
    private final ReviewLikeService reviewLikeService;

    private final ReviewSummaryService reviewSummaryService;

    @Transactional
    public void createReview(ReviewCreateRequest req, Long userId) {
        if (!req.getResult().isValidType(req.getCategory())) {
            throw new AppException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST);
        }

        Review review = Review.builder()
                .title(req.getTitle())
                .rate(req.getRate())
                .result(req.getResult())
                .generation(req.getGeneration())
                .category(req.getCategory())
                .jobId(req.getJobId())
                .clubId(req.getClubId())
                .userId(userId)
                .likeCount(0L)
                .commentCount(0L)
                .build();

        Review savedReview = reviewRepository.save(review);
        List<ReviewAnswer> answers = reviewAnswerGenerator.generate(review, req.getAnswers());

        reviewAnswerRepository.saveAll(answers);
        reviewSummaryService.createReviewSummary(savedReview, req.getAnswers());
    }

    @Transactional(readOnly = true)
    public Page<ReviewSummaryResponse> search(ReviewSearchRequest request, Pageable pageable) {
        return queryReviewRepository.search(request, pageable);
    }

    @Transactional(readOnly = true)
    public ReviewView getReview(Long reviewId) {
        OriginalReviewDetailView review = queryReviewRepository.findReviewById(reviewId);
        List<ReviewAnswerResponse> reviewAnswerResponses = reviewAnswerConverter.toResponses(review.getAnswers());
        return new ReviewView(
                review.getTitle(),
                review.getRate(),
                review.getResult(),
                review.getJob(),
                review.getClub(),
                review.getGeneration(),
                reviewAnswerResponses);
    }

}