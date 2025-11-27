package com.moyeoit.context.review.infra;

import com.moyeoit.context.review.domain.model.ReviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAnswerRepository extends JpaRepository<ReviewAnswer, Long> {
}
