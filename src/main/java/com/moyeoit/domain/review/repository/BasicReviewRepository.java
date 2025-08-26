package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.BasicReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicReviewRepository extends JpaRepository<BasicReview, Long>, BasicReviewRepositoryCustom {
    @Modifying
    @Query("UPDATE BasicReview b SET b.likeCount = b.likeCount + 1 WHERE b.id = :id")
    void plusLikeCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE BasicReview b SET b.likeCount = b.likeCount - 1 WHERE b.id = :id")
    void minusLikeCount(@Param("id") Long id);
}
