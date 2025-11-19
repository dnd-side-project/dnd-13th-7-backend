package com.moyeoit.domain.review.infra;

import com.moyeoit.domain.review.domain.model.ReviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAnswerRepository extends JpaRepository<ReviewAnswer, Long> {
}
