package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.review.domain.BasicReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicReviewRepository extends JpaRepository<BasicReview, Long> {
}
