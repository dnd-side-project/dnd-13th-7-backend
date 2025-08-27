package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.review.domain.ReviewLike;
import com.moyeoit.domain.review.domain.ReviewType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findReviewLikeByAppUserAndReviewIdAndReviewType(AppUser appUser, Long reviewId,
                                                                         ReviewType reviewType);

    Integer countByReviewIdAndReviewType(Long reviewId, ReviewType reviewType);

    @Query("SELECT count(rl) FROM ReviewLike rl WHERE rl.appUser.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
}
