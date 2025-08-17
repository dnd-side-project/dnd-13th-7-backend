package com.moyeoit.domain.review.Repository;

import com.moyeoit.domain.review.domain.BasicReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicReviewRepository extends JpaRepository<BasicReview,Long>,BasicReviewRepositoryCustom {
}
