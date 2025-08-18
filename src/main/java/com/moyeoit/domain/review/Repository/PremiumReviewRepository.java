package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.PremiumReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewRepository extends JpaRepository<PremiumReview, Long>, PremiumReviewRepositoryCustom {
}
