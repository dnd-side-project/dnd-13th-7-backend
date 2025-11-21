package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.model.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    @Query("SELECT rc FROM ReviewComment rc WHERE rc.id = :reviewCommentId AND rc.deleted = false")
    Optional<ReviewComment> findById(@Param("reviewCommentId") Long reviewCommentId);

}