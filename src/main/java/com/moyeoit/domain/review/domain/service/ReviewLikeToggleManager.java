package com.moyeoit.domain.review.domain.service;

import com.moyeoit.domain.review.controller.response.ReviewLikeResponse;
import com.moyeoit.domain.review.domain.ReviewLike;
import com.moyeoit.domain.review.domain.model.Review;
import com.moyeoit.domain.review.infra.ReviewRepository;
import com.moyeoit.domain.review.repository.ReviewLikeRepository;
import com.moyeoit.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewLikeToggleManager {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public ReviewLikeResponse toggle(Review review, User user) {
        // 기존 좋아요 여부 조회
        Optional<ReviewLike> existingLike = reviewLikeRepository.findReviewLikeByUserIdAndReviewId(user.getId(), review.getId());
        if (existingLike.isPresent()) return deleteLike(review, existingLike.get());
        return addLike(review, user);
    }

    private ReviewLikeResponse deleteLike(Review review, ReviewLike reviewLike) {
        try {
            reviewLikeRepository.delete(reviewLike);
            reviewLikeRepository.flush();

            reviewRepository.decreaseLikeCount(review.getId());

            return new ReviewLikeResponse(false, review.getLikeCount() - 1);
        } catch (OptimisticLockingFailureException e) {
            log.info("좋아요 취소가 중복되어, 현재 요청을 무시합니다. (reviewId={})", review.getId());
            return new ReviewLikeResponse(false, review.getLikeCount());
        }
    }

    private ReviewLikeResponse addLike(Review review, User user) {
        try {
            ReviewLike reviewLike = ReviewLike.builder()
                    .userId(user.getId())
                    .reviewId(review.getId())
                    .build();

            reviewLikeRepository.save(reviewLike);
            reviewRepository.increaseLikeCount(review.getId());
            return new ReviewLikeResponse(true, review.getLikeCount() + 1);
        } catch (DataIntegrityViolationException e) {
            log.info("좋아요 요청이 중복되어, 현재 요청을 무시합니다. (reviewId={})", review.getId());
            return new ReviewLikeResponse(true, review.getLikeCount());
        }
    }

}
