package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.PremiumReviewComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewCommentRepository extends JpaRepository<PremiumReviewComment, Long> {

    @Query("""
                SELECT prc
                FROM PremiumReviewComment prc
                LEFT JOIN FETCH prc.appUser
                WHERE prc.premiumReview.id = :premiumReviewId
            """)
    List<PremiumReviewComment> findComments(@Param("premiumReviewId") Long premiumReviewId);


}
