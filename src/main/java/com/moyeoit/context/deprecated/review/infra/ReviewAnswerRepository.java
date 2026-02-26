package com.moyeoit.context.deprecated.review.infra;

import com.moyeoit.context.deprecated.review.domain.model.ReviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAnswerRepository extends JpaRepository<ReviewAnswer, Long> {
}
