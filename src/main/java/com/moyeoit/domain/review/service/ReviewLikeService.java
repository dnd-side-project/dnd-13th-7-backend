package com.moyeoit.domain.review.service;


import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.review.controller.response.ReviewLikeResponse;
import com.moyeoit.domain.review.domain.ReviewLike;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.repository.BasicReviewRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.domain.review.repository.ReviewLikeRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final AppUserRepository appUserRepository;
    private final BasicReviewRepository basicReviewRepository;
    private final PremiumReviewRepository premiumReviewRepository;

    @Transactional
    public ReviewLikeResponse toggleLike(Long reviewId, String reviewType, Long userId) {
        ReviewType type = ReviewType.fromString(reviewType);
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        Optional<ReviewLike> existingLike = reviewLikeRepository.findReviewLikeByAppUserAndReviewIdAndReviewType(user, reviewId, type);

        boolean liked = existingLike
                .map(like -> {
                    reviewLikeRepository.delete(like);
                    if (type == ReviewType.BASIC) {
                        basicReviewRepository.minusLikeCount(reviewId);
                    }
                    if(type == ReviewType.PREMIUM) {
                        premiumReviewRepository.minusLikeCount(reviewId);
                    }
                    return false;
                })
                .orElseGet(() -> {
                    ReviewLike reviewLike = ReviewLike.builder()
                            .appUser(user)
                            .reviewId(reviewId)
                            .reviewType(type)
                            .build();
                    reviewLikeRepository.save(reviewLike);

                    if (type == ReviewType.BASIC) {
                        basicReviewRepository.plusLikeCount(reviewId);
                    }
                    if(type == ReviewType.PREMIUM){
                        premiumReviewRepository.plusLikeCount(reviewId);
                    }
                    return true;
                });

        int likeCount = reviewLikeRepository.countByReviewIdAndReviewType(reviewId, type);

        return new ReviewLikeResponse(liked, likeCount);
    }
}
