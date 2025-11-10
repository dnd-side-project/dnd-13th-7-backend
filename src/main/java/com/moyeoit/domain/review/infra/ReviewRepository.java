package com.moyeoit.domain.review.infra;

import com.moyeoit.domain.review.domain.v2.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
