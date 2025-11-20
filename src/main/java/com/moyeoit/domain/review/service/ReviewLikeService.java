package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.response.ReviewLikeResponse;
import com.moyeoit.domain.review.domain.model.Review;
import com.moyeoit.domain.review.domain.service.ReviewLikeToggleManager;
import com.moyeoit.domain.review.infra.ReviewRepository;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeToggleManager reviewLikeToggleManager;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewLikeResponse toggleLike(Long userId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND));

        return reviewLikeToggleManager.toggle(review, user);
    }

}
