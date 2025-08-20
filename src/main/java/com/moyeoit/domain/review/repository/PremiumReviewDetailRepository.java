package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewDetailRepository extends JpaRepository<PremiumReviewDetail, Long> {
}
