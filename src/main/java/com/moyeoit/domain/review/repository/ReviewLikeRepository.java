package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findReviewLikeByUserIdAndReviewId(Long userId, Long reviewId);

    @Query("SELECT count(rl) FROM ReviewLike rl WHERE rl.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);

}
