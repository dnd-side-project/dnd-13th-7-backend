package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.ReviewContentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSummaryRepository extends JpaRepository<ReviewContentSummary, Long> {
}
