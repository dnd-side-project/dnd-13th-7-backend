package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.ReviewCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            SELECT br
            FROM BasicReview br
            LEFT JOIN FETCH br.basicReviewDetails brd
            LEFT JOIN FETCH br.club c
            LEFT JOIN FETCH c.recruitment
            LEFT JOIN FETCH br.job j
            LEFT JOIN FETCH br.user u
            LEFT JOIN FETCH brd.question q
            WHERE br.user.id = :userId AND br.reviewCategory = :reviewCategory
            """)
    Page<BasicReview> findBasicReviewByUserId(@Param("userId") Long userId,
                                              @Param("reviewCategory") ReviewCategory reviewCategory,
                                              Pageable pageable);
}
