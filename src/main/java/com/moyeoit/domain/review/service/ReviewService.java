package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.MyReviewSearchRequest;
import com.moyeoit.domain.review.controller.response.AnswerResponse;
import com.moyeoit.domain.review.controller.response.BasicReviewResponse;
import com.moyeoit.domain.review.controller.response.MultipleChoiceAnswerResponse;
import com.moyeoit.domain.review.controller.response.PremiumReviewResponse;
import com.moyeoit.domain.review.controller.response.ReviewResponse;
import com.moyeoit.domain.review.controller.response.SingleChoiceAnswerResponse;
import com.moyeoit.domain.review.controller.response.SubjectiveAnswerResponse;
import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.PremiumReview;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.repository.BasicReviewRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final BasicReviewRepository basicReviewRepository;
    private final PremiumReviewRepository premiumReviewRepository;

    public Page<ReviewResponse> getReview(MyReviewSearchRequest request, Long userId, Pageable pageable) {

        if (Objects.equals(request.getReviewType(), ReviewType.BASIC)) {
            Page<BasicReview> reviews = basicReviewRepository.findBasicReviewByUserId(userId,
                    request.getReviewCategory(), pageable);

            return reviews.map(review -> {
                List<AnswerResponse> answers = review.getBasicReviewDetails()
                        .stream()
                        .map(this::createAnswerResponse)
                        .toList();
                return BasicReviewResponse.from(review, answers);
            });

        }

        if (Objects.equals(request.getReviewType(), ReviewType.PREMIUM)) {
            Page<PremiumReview> reviews = premiumReviewRepository.findPremiumReviewByUserIdAndReviewCategory(userId,
                    request.getReviewCategory(),
                    pageable);

            return reviews.map(review -> {
                List<AnswerResponse> answers = review.getPremiumReviewDetails()
                        .stream()
                        .map(PremiumReviewService::createAnswerResponse)
                        .toList();
                return PremiumReviewResponse.from(review, answers);
            });
        }

        throw new AppException(ReviewErrorCode.NOT_FOUND_TYPE);
    }

    private AnswerResponse createAnswerResponse(BasicReviewDetail detail) {
        if (AnswerType.TEXT.equals(detail.getAnswerType())) {
            return SubjectiveAnswerResponse.from(detail);
        }

        if (AnswerType.INTEGER.equals(detail.getAnswerType())) {
            return SingleChoiceAnswerResponse.from(detail);
        }

        if (AnswerType.ARRAY_INTEGER.equals(detail.getAnswerType())) {
            return MultipleChoiceAnswerResponse.from(detail);
        }

        throw new AppException(ReviewErrorCode.NOT_FOUND);
    }

}