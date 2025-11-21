package com.moyeoit.domain.review.infra;

import com.moyeoit.domain.review.domain.dto.ReviewCommentWithUserDto;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moyeoit.domain.review.domain.model.QReviewComment.reviewComment;
import static com.moyeoit.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ReviewCommentWithUserDto> getReviewCommentsByReviewId(Long reviewId) {
        return queryFactory
                .select(selectReviewCommentWithUserDto())
                .from(reviewComment)
                .leftJoin(user).on(reviewComment.userId.eq(user.id))
                .where(reviewComment.review.id.eq(reviewId))
                .fetch();
    }

    private ConstructorExpression<ReviewCommentWithUserDto> selectReviewCommentWithUserDto() {
        return Projections.constructor(ReviewCommentWithUserDto.class,
                reviewComment.id,
                user.nickname,
                user.profileImageUrl,
                reviewComment.content,
                reviewComment.createdDate,
                reviewComment.parent.id,
                reviewComment.deleted
        );
    }


}
