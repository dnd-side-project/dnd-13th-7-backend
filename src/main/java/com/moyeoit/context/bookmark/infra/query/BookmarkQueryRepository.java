package com.moyeoit.context.bookmark.infra.query;

import static com.moyeoit.context.bookmark.infra.entity.QBookmarkEntity.bookmarkEntity;
import static com.moyeoit.context.club.domain.entity.QClub.club;
import static com.moyeoit.context.club.domain.entity.position.QClubPosition.clubPosition;
import static com.moyeoit.context.review.domain.model.QReview.review;
import static com.moyeoit.context.review.domain.model.QReviewContentSummary.reviewContentSummary;
import static com.moyeoit.context.user.domain.QJob.job;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.moyeoit.context.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import com.moyeoit.context.review.presentation.response.BlogReviewResponse;
import com.moyeoit.context.review.presentation.response.ReviewSummaryResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ClubListResponse> findBookmarkedClubs(Long userId, Pageable pageable) {
        List<Long> clubIds = queryFactory
                .select(club.id)
                .from(bookmarkEntity)
                .join(club).on(bookmarkEntity.targetId.eq(club.id))
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.CLUB),
                        bookmarkEntity.isActive.isTrue()
                )
                .orderBy(bookmarkEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (clubIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        List<ClubListResponse> pagedContent = queryFactory
                .selectFrom(club)
                .leftJoin(club.positions, clubPosition)
                .where(club.id.in(clubIds))
                .transform(
                        groupBy(club.id).list(
                                Projections.constructor(ClubListResponse.class,
                                        club.id,
                                        club.name,
                                        club.clubProfile.bio,
                                        list(clubPosition.name),
                                        club.clubProfile.imageUrl,
                                        club.recruiting
                                )
                        )
                );
        
        List<ClubListResponse> sortedContent = clubIds.stream()
                .map(id -> pagedContent.stream()
                        .filter(c -> c.clubId().equals(id))
                        .findFirst()
                        .orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();

        Long total = queryFactory
                .select(bookmarkEntity.count())
                .from(bookmarkEntity)
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.CLUB),
                        bookmarkEntity.isActive.isTrue()
                )
                .fetchOne();

        return new PageImpl<>(sortedContent, pageable, total != null ? total : 0);
    }

    public Page<ReviewSummaryResponse> findBookmarkedReviews(Long userId, BookmarkType type, Pageable pageable) {
        List<Long> reviewIds = queryFactory
                .select(review.id)
                .from(bookmarkEntity)
                .join(review).on(bookmarkEntity.targetId.eq(review.id))
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(type),
                        bookmarkEntity.isActive.isTrue()
                )
                .orderBy(bookmarkEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (reviewIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        List<ReviewSummaryResponse> sortedContent = queryFactory
                .select(createReviewSummary())
                .from(review)
                .join(bookmarkEntity).on(bookmarkEntity.targetId.eq(review.id)
                        .and(bookmarkEntity.type.eq(type))
                        .and(bookmarkEntity.userId.eq(userId)))
                .leftJoin(club).on(review.clubId.eq(club.id))
                .leftJoin(job).on(review.jobId.eq(job.id))
                .leftJoin(reviewContentSummary).on(review.id.eq(reviewContentSummary.review.id))
                .where(review.id.in(reviewIds))
                .orderBy(bookmarkEntity.createdDate.desc())
                .fetch();

        Long total = queryFactory
                .select(bookmarkEntity.count())
                .from(bookmarkEntity)
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(type),
                        bookmarkEntity.isActive.isTrue()
                )
                .fetchOne();

        return new PageImpl<>(sortedContent, pageable, total != null ? total : 0);
    }

    public Page<BlogReviewResponse> findBookmarkedBlogReviews(Long userId, Pageable pageable) {
        List<Long> reviewIds = queryFactory
                .select(review.id)
                .from(bookmarkEntity)
                .join(review).on(bookmarkEntity.targetId.eq(review.id))
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.BLOG_REVIEW),
                        bookmarkEntity.isActive.isTrue()
                )
                .orderBy(bookmarkEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (reviewIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        List<BlogReviewResponse> sortedContent = queryFactory
                .select(createBlogReviewResponse())
                .from(review)
                .join(bookmarkEntity).on(bookmarkEntity.targetId.eq(review.id)
                        .and(bookmarkEntity.type.eq(BookmarkType.BLOG_REVIEW))
                        .and(bookmarkEntity.userId.eq(userId)))
                .leftJoin(club).on(review.clubId.eq(club.id))
                .leftJoin(job).on(review.jobId.eq(job.id))
                .leftJoin(reviewContentSummary).on(review.id.eq(reviewContentSummary.review.id))
                .where(review.id.in(reviewIds))
                .orderBy(bookmarkEntity.createdDate.desc())
                .fetch();

        Long total = queryFactory
                .select(bookmarkEntity.count())
                .from(bookmarkEntity)
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.BLOG_REVIEW),
                        bookmarkEntity.isActive.isTrue()
                )
                .fetchOne();

        return new PageImpl<>(sortedContent, pageable, total != null ? total : 0);
    }

    private ConstructorExpression<ReviewSummaryResponse> createReviewSummary() {
        return Projections.constructor(ReviewSummaryResponse.class,
                club.name,
                review.generation,
                job.name,
                review.rate,
                review.title,
                reviewContentSummary.choiceSummary,
                review.likeCount,
                review.commentCount);
    }

    private ConstructorExpression<BlogReviewResponse> createBlogReviewResponse() {
        return Projections.constructor(BlogReviewResponse.class,
                club.name,
                review.generation,
                job.name,
                review.title,
                reviewContentSummary.subjectiveSummary, // url
                Expressions.nullExpression(String.class), // imageUrl
                Expressions.nullExpression(String.class), // description
                Expressions.nullExpression(String.class)  // blogName
        );
    }
}
