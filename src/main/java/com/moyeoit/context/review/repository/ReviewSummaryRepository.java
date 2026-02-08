package com.moyeoit.context.review.repository;

import com.moyeoit.context.review.domain.model.ReviewContentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSummaryRepository extends JpaRepository<ReviewContentSummary, Long> {
}
