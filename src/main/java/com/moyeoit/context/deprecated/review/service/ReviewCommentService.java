package com.moyeoit.context.deprecated.review.service;

import com.moyeoit.context.deprecated.review.domain.dto.ReviewCommentWithUserDto;
import com.moyeoit.context.deprecated.review.domain.model.Review;
import com.moyeoit.context.deprecated.review.domain.model.ReviewComment;
import com.moyeoit.context.deprecated.review.infra.ReviewCommentQueryRepository;
import com.moyeoit.context.deprecated.review.infra.ReviewRepository;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentCreateRequest;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentResponse;
import com.moyeoit.context.deprecated.review.presentation.request.comment.ReviewCommentUpdateRequest;
import com.moyeoit.context.deprecated.review.repository.ReviewCommentRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewCommentService {

    private final ReviewCommentQueryRepository reviewCommentQueryRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<ReviewCommentResponse> getReviewComments(Long reviewId) {
        List<ReviewCommentWithUserDto> results = reviewCommentQueryRepository.getReviewCommentsByReviewId(reviewId);

        Map<Long, ReviewCommentResponse> map = new HashMap<>();
        List<ReviewCommentResponse> roots = new ArrayList<>();

        for (ReviewCommentWithUserDto dto : results) {
            ReviewCommentResponse reviewCommentResponse = ReviewCommentResponse.from(dto);
            map.put(dto.getReviewCommentId(), reviewCommentResponse);
        }

        for (ReviewCommentWithUserDto dto : results) {
            ReviewCommentResponse current = map.get(dto.getReviewCommentId());
            if (dto.getParentId() == null) {
                roots.add(current);
            } else {
                ReviewCommentResponse parent = map.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }

        return roots;
    }

    /**
     * 댓글 생성
     */
    @Transactional
    public void createReviewComment(ReviewCommentCreateRequest req,
                                    Long userId) {
        Review review = reviewRepository.findById(req.getReviewId())
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND));

        ReviewComment comment;
        if (req.getParentCommentId() != null) {
            ReviewComment parent = reviewCommentRepository.findById(req.getParentCommentId())
                    .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND_REVIEW_COMMENT));

            comment = ReviewComment.builder()
                    .review(review)
                    .content(req.getContent())
                    .parent(parent)
                    .userId(userId)
                    .deleted(false)
                    .build();
        } else {
            comment = ReviewComment.builder()
                    .review(review)
                    .content(req.getContent())
                    .deleted(false)
                    .userId(userId)
                    .build();
        }
        reviewCommentRepository.save(comment);
        reviewRepository.increaseCommentCount(req.getReviewId());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteReviewComment(Long reviewCommentId,
                                    Long userId) {
        ReviewComment comment = reviewCommentRepository.findById(reviewCommentId)
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND_REVIEW_COMMENT));

        if (!comment.isAuthor(userId))
            throw new AppException(ReviewErrorCode.NOT_REVIEW_COMMENT_OWNER);

        comment.delete();
        reviewRepository.decreaseCommentCount(comment.getReview().getId());
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateReviewComment(Long commentId,
                                    ReviewCommentUpdateRequest req,
                                    Long userId) {
        ReviewComment comment = reviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND_REVIEW_COMMENT));

        if (!comment.isAuthor(userId))
            throw new AppException(ReviewErrorCode.NOT_REVIEW_COMMENT_OWNER);

        comment.updateContent(req.getContent());
    }
}
