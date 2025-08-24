package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.PremiumReview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewRepository extends JpaRepository<PremiumReview, Long>, PremiumReviewRepositoryCustom {

    @Query("SELECT pr FROM PremiumReview pr LEFT JOIN FETCH pr.premiumReviewDetails prd "
            + "LEFT JOIN FETCH pr.job j "
            + "LEFT JOIN FETCH pr.club c "
            + "LEFT JOIN  FETCH pr.user prau "
            + "LEFT JOIN FETCH prd.appUser prdau "
            + "LEFT JOIN FETCH prd.question q "
            + "LEFT JOIN FETCH q.questionElements qe "
            + "WHERE pr.id = :reviewId")
    Optional<PremiumReview> findById(@Param("reviewId") Long reviewId);

    @Query("""
            SELECT pr
            FROM PremiumReview pr
            LEFT JOIN FETCH pr.premiumReviewDetails prd
            LEFT JOIN FETCH pr.club c
            LEFT JOIN FETCH c.recruitment
            LEFT JOIN FETCH pr.job j
            LEFT JOIN FETCH pr.user u
            LEFT JOIN FETCH prd.question q
            WHERE pr.id = :id
            """)
    Optional<PremiumReview> findDetailSkeleton(@Param("id") Long id);
}
