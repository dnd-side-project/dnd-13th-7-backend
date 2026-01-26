package com.moyeoit.context.review.infra;

import com.moyeoit.context.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.review.domain.enums.ReviewSort;
import com.moyeoit.context.review.presentation.request.BlogReviewSearchRequest;
import com.moyeoit.context.review.presentation.response.BlogReviewResponseV2;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moyeoit.context.bookmark.infra.entity.QBookmarkEntity.bookmarkEntity;
import static com.moyeoit.context.club.domain.entity.QClub.club;
import static com.moyeoit.context.review.domain.model.QBlogReviewEntity.blogReviewEntity;
import static com.moyeoit.context.user.domain.QJob.job;
import static com.querydsl.core.group.GroupBy.groupBy;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BlogReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 검색 Query
     *
     *
     */
    public Page<BlogReviewResponseV2> search(BlogReviewSearchRequest request, Pageable pageable) {

        List<BlogReviewResponseV2> contents = queryFactory
                .select()
                .from(blogReviewEntity)
                .leftJoin(club).on(blogReviewEntity.clubId.eq(club.id))
                .leftJoin(job).on(blogReviewEntity.jobId.eq(job.id))
                .where(eqTitle(request.getTitle()),
                        eqClubId(request.getClubId()),
                        eqJobId(request.getJobId()),
                        eqGeneration(request.getGeneration()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(getOrderSpecifier(request.getSort()))
                .transform(
                        groupBy(blogReviewEntity.id).list(createBlogReviewResponse())
                );

        Long totalCount = queryFactory
                .select(blogReviewEntity.count())
                .from(blogReviewEntity)
                .where(eqTitle(request.getTitle()),
                       eqClubId(request.getClubId()),
                       eqJobId(request.getJobId()),
                       eqGeneration(request.getGeneration()))
                .fetchOne();

        return new PageImpl<>(contents, pageable, totalCount == null ? 0 : totalCount);
    }

    public ConstructorExpression<BlogReviewResponseV2> createBlogReviewResponse() {
        return Projections.constructor(BlogReviewResponseV2.class,
                blogReviewEntity.id,
                club.name,
                job.name,
                blogReviewEntity.title,
                blogReviewEntity.blogName,
                blogReviewEntity.generation,
                blogReviewEntity.blogUrl,
                blogReviewEntity.imageUrl,
                blogReviewEntity.createdDate,
                blogReviewEntity.updateDate
                );
    }

    public BooleanExpression eqTitle(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return blogReviewEntity.title.containsIgnoreCase(title);
    }

    public BooleanExpression eqClubId(Long clubId) {
        return clubId == null ? null : blogReviewEntity.clubId.eq(clubId);
    }

    public BooleanExpression eqJobId(Long jobId) {
        return jobId == null ? null : blogReviewEntity.jobId.eq(jobId);
    }

    public BooleanExpression eqGeneration(Integer generation) {
        return generation == null ? null : blogReviewEntity.generation.eq(generation);
    }

    public OrderSpecifier<?>[] getOrderSpecifier(ReviewSort sort) {
        if (ReviewSort.LATEST.equals(sort))
            return new OrderSpecifier[]{blogReviewEntity.createdDate.desc()};
        if (ReviewSort.POPULAR.equals(sort))
            return new OrderSpecifier[]{
                    new OrderSpecifier<>(Order.DESC, bookmarkCountSubQuery()),
                    blogReviewEntity.createdDate.desc()
            };
        return new OrderSpecifier[]{blogReviewEntity.createdDate.desc()};
    }

    private com.querydsl.core.types.SubQueryExpression<Long> bookmarkCountSubQuery() {
        return JPAExpressions
                .select(bookmarkEntity.count())
                .from(bookmarkEntity)
                .where(
                        bookmarkEntity.targetId.eq(blogReviewEntity.id),
                        bookmarkEntity.type.eq(BookmarkType.BLOG_REVIEW),
                        bookmarkEntity.isActive.isTrue()
                );
    }

}
