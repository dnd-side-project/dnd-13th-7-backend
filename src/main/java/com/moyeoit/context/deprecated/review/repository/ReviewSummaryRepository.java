package com.moyeoit.context.deprecated.review.repository;

import com.moyeoit.context.deprecated.review.domain.model.ReviewContentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSummaryRepository extends JpaRepository<ReviewContentSummary, Long> {
}
