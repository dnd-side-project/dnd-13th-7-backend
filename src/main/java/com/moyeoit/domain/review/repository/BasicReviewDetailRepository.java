package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.BasicReviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicReviewDetailRepository extends JpaRepository<BasicReviewDetail, Long> {

}
