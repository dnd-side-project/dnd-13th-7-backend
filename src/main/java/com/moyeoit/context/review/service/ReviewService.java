package com.moyeoit.context.review.service;

import com.moyeoit.context.review.controller.response.v2.OriginalReviewDetailView;
import com.moyeoit.context.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.context.review.domain.model.Review;
import com.moyeoit.context.review.domain.model.ReviewAnswer;
import com.moyeoit.context.review.domain.service.ReviewAnswerConverter;
import com.moyeoit.context.review.infra.ReviewAnswerRepository;
import com.moyeoit.context.review.infra.ReviewQueryRepository;
import com.moyeoit.context.review.infra.ReviewRepository;
import com.moyeoit.context.review.infra.generator.ReviewAnswerGenerator;
import com.moyeoit.context.review.presentation.request.ReviewCreateRequest;
import com.moyeoit.context.review.presentation.request.ReviewSearchRequest;
import com.moyeoit.context.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.context.review.presentation.response.ReviewView;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import com.moyeoit.global.time.TimeProvider;
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
    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewSummaryService reviewSummaryService;
    private final TimeProvider timeProvider;

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
    public Page<ReviewSummaryResponse> search(ReviewSearchRequest request, Pageable pageable, Long userId) {
        return reviewQueryRepository.search(request, pageable, userId);
    }

    @Transactional(readOnly = true)
    public ReviewView getReview(Long reviewId) {
        OriginalReviewDetailView review = reviewQueryRepository.findReviewById(reviewId);
        List<ReviewAnswerResponse> reviewAnswerResponses = reviewAnswerConverter.toResponses(review.getAnswers());
        return new ReviewView(
                review.getTitle(),
                review.getRate(),
                review.getResult(),
                review.getJob(),
                review.getClub(),
                review.getGeneration(),
                review.getLikeCount(),
                review.getCommentCount(),
                reviewAnswerResponses
        );
    }

    @Transactional
    public void delete(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND));

        if (!review.isAuthor(userId)) {
            throw new AppException(ReviewErrorCode.NOT_REVIEW_OWNER);
        }

        review.delete(timeProvider.now());
        // TODO : 관련된 ReviewLike 모두 삭제
    }

}