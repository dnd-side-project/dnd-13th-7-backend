package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.controller.request.v2.ReviewCreateRequest;
import com.moyeoit.domain.review.controller.response.v2.OriginalReviewDetailView;
import com.moyeoit.domain.review.controller.response.v2.ReviewAnswerResponse;
import com.moyeoit.domain.review.controller.response.v2.ReviewSummary;
import com.moyeoit.domain.review.controller.response.v2.ReviewView;
import com.moyeoit.domain.review.domain.service.ReviewAnswerConverter;
import com.moyeoit.domain.review.domain.v2.Review;
import com.moyeoit.domain.review.domain.v2.ReviewAnswer;
import com.moyeoit.domain.review.infra.ReviewAnswerRepository;
import com.moyeoit.domain.review.infra.ReviewRepository;
import com.moyeoit.domain.review.infra.ReviewRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceV2 {

    private final ReviewRepository reviewRepository;
    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ReviewAnswerConverter reviewAnswerConverter;
    private final ReviewRepositoryImpl reviewRepositoryImpl;

    @Transactional
    public void createReview(ReviewCreateRequest req, Long userId) {
        Review review = Review.builder()
                .generation(req.getGeneration())
                .category(req.getCategory())
                .jobId(req.getJobId())
                .clubId(req.getClubId())
                .userId(userId)
                .build();

        Review savedReview = reviewRepository.save(review);
        List<ReviewAnswer> answers  = reviewAnswerConverter.generate(review, req.getAnswers());

        reviewAnswerRepository.saveAll(answers);
    }

    @Transactional(readOnly = true)
    public List<ReviewSummary> getReivews(ReviewPagingRequest request) {
        return reviewRepositoryImpl.search(request, null);
    }

    @Transactional(readOnly = true)
    public ReviewView getReview(Long reviewId) {
        OriginalReviewDetailView originalReviewView = reviewRepositoryImpl.findReviewById(reviewId);
        List<ReviewAnswerResponse> reviewAnswerResponses = reviewAnswerConverter.toResponses(originalReviewView.getAnswers());
        return new ReviewView(originalReviewView.getJob(), originalReviewView.getClub(), originalReviewView.getGeneration(), reviewAnswerResponses);
    }

}