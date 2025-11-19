package com.moyeoit.domain.review.infra;

import com.moyeoit.domain.review.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
