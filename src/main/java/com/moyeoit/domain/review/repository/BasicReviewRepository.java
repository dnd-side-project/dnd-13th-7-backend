package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.BasicReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicReviewRepository extends JpaRepository<BasicReview, Long>, BasicReviewRepositoryCustom {
}
