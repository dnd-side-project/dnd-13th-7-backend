package com.moyeoit.context.community.infra.query;

import static com.moyeoit.context.community.domain.QPost.post;
import static com.moyeoit.context.community.domain.QPostImage.postImage;
import static com.moyeoit.context.community.domain.QPostLike.postLike;

import com.moyeoit.context.community.domain.CommunityCategoryType;
import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.presentation.controller.response.PopularPostResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.community.presentation.controller.response.PostDetailInfoResponse;
import com.moyeoit.context.community.presentation.controller.response.QPopularPostResponse;
import com.moyeoit.context.community.presentation.controller.response.QPostCardResponse;
import com.moyeoit.context.community.presentation.controller.response.QPostDetailInfoResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final int POPULAR_VIEW_THRESHOLD = 30;
    private static final int POPULAR_LIKE_THRESHOLD = 3;
    private static final int POPULAR_COMMENT_THRESHOLD = 2;
    private static final int POPULAR_MAX_SIZE = 20;
    private static final int POPULAR_DAYS = 30;

    public Page<PostCardResponse> findFeed(CommunityCategoryType category, Pageable pageable) {
        boolean popularCategory = isPopularCategory(category);
        long limit = resolvePopularLimit(popularCategory, pageable);

        List<PostCardResponse> content = queryFactory
                .select(new QPostCardResponse(
                        post.id,
                        post.title,
                        post.content.substring(0, 100),
                        JPAExpressions
                                .select(postImage.imageUrl)
                                .from(postImage)
                                .where(postImage.post.eq(post), postImage.isRepresentative.isTrue()),
                        post.category.id,
                        post.category.name,
                        post.postType,
                        post.author.nickname,
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.createdAt
                ))
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        eqCategoryName(category),
                        filterHotPost(category)
                )
                .orderBy(getOrderSpecifiers(popularCategory, pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(limit)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        eqCategoryName(category),
                        filterHotPost(category)
                );

        if (popularCategory) {
            return PageableExecutionUtils.getPage(content, pageable, () -> capPopularCount(countQuery.fetchOne()));
        }

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    public Page<PopularPostResponse> findPopular(Pageable pageable) {
        long limit = resolvePopularLimit(true, pageable);

        List<PopularPostResponse> content = queryFactory
                .select(new QPopularPostResponse(
                        post.id,
                        post.title,
                        post.content.substring(0, 100),
                        post.category.id,
                        post.category.name,
                        post.postType,
                        post.likeCount,
                        post.commentCount
                ))
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        popularPostPredicate())
                .orderBy(popularOrderSpecifiers())
                .offset(pageable.getOffset())
                .limit(limit)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        popularPostPredicate());

        return PageableExecutionUtils.getPage(content, pageable, () -> capPopularCount(countQuery.fetchOne()));
    }

    public PostDetailInfoResponse findPostDetailInfo(Long postId, Long userId) {
        PostDetailInfoResponse result = queryFactory
                .select(new QPostDetailInfoResponse(
                        post.category.name,
                        post.commentCount,
                        post.content,
                        post.createdAt,
                        Expressions.constant(new ArrayList<>()),
                        buildIsHotPostExpression(),
                        buildIsLikedExpression(postId, userId),
                        post.likeCount,
                        post.author.nickname,
                        post.postType,
                        post.title,
                        post.viewCount
                ))
                .from(post)
                .where(post.id.eq(postId), post.isDeleted.isFalse())
                .fetchOne();

        List<String> imageUrls = queryFactory
                .select(postImage.imageUrl)
                .from(postImage)
                .where(postImage.post.id.eq(postId))
                .orderBy(postImage.orderIndex.asc())
                .fetch();

        result.setImages(imageUrls);

        return result;
    }

    public Page<PostCardResponse> searchPostCards(String keyword, Pageable pageable) {
        List<PostCardResponse> content = queryFactory
                .select(new QPostCardResponse(
                        post.id,
                        post.title,
                        post.content.substring(0, 100),
                        JPAExpressions
                                .select(postImage.imageUrl)
                                .from(postImage)
                                .where(postImage.post.eq(post), postImage.isRepresentative.isTrue()),
                        post.category.id,
                        post.category.name,
                        post.postType,
                        post.author.nickname,
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.createdAt
                ))
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        containsTitle(keyword)
                        )
                .orderBy(post.createdAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        containsTitle(keyword)
                );

        return PageableExecutionUtils.getPage(content, pageable , countQuery::fetchOne);
    }

    private BooleanExpression eqCategoryName(CommunityCategoryType category) {
        if (category == null) {
            return null;
        }
        if (category.isPopular()) {
            return null;
        }
        return post.category.name.eq(category.getDisplayName());
    }

    private BooleanExpression filterHotPost(CommunityCategoryType category) {
        if (category != null && category.isPopular()) {
            return popularPostPredicate();
        }
        return null;
    }

    private BooleanExpression buildIsLikedExpression(Long postId, Long viewerId) {
        if (viewerId == null) {
            return Expressions.FALSE;
        }

        return new CaseBuilder()
                .when(JPAExpressions
                        .selectOne()
                        .from(postLike)
                        .where(
                                postLike.targetType.eq(PostLike.TargetType.POST),
                                postLike.targetId.eq(postId),
                                postLike.userId.eq(viewerId)
                        )
                        .exists()
                ).then(true)
                .otherwise(false);
    }

    private BooleanExpression containsTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return post.title.containsIgnoreCase(keyword);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(boolean popularCategory, Sort sort) {
        if (popularCategory) {
            return popularOrderSpecifiers();
        }
        return new OrderSpecifier<?>[]{getOrderSpecifier(sort)};
    }

    private OrderSpecifier<?>[] popularOrderSpecifiers() {
        return new OrderSpecifier<?>[]{
                post.viewCount.desc(),
                post.likeCount.desc(),
                post.createdAt.desc()
        };
    }

    private OrderSpecifier<?> getOrderSpecifier(Sort sort) {
        if (sort.isEmpty()) {
            return post.createdAt.desc();
        }

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            switch (order.getProperty()) {
                case "likeCount":
                    return new OrderSpecifier<>(direction, post.likeCount);
                case "viewCount":
                    return new OrderSpecifier<>(direction, post.viewCount);
                case "createdAt":
                    return new OrderSpecifier<>(direction, post.createdAt);
            }
        }
        return post.createdAt.desc();
    }

    private BooleanExpression popularPostPredicate() {
        return post.viewCount.goe(POPULAR_VIEW_THRESHOLD)
                .and(post.likeCount.goe(POPULAR_LIKE_THRESHOLD))
                .and(post.commentCount.goe(POPULAR_COMMENT_THRESHOLD))
                .and(post.createdAt.goe(LocalDateTime.now().minusDays(POPULAR_DAYS)));
    }

    private BooleanExpression buildIsHotPostExpression() {
        return new CaseBuilder()
                .when(popularPostPredicate())
                .then(true)
                .otherwise(false);
    }

    private boolean isPopularCategory(CommunityCategoryType category) {
        return category != null && category.isPopular();
    }

    private long resolvePopularLimit(boolean popularCategory, Pageable pageable) {
        if (!popularCategory) {
            return pageable.getPageSize();
        }
        long offset = pageable.getOffset();
        if (offset >= POPULAR_MAX_SIZE) {
            return 0;
        }
        return Math.min(pageable.getPageSize(), POPULAR_MAX_SIZE - offset);
    }

    private long capPopularCount(Long total) {
        long value = total == null ? 0L : total;
        return Math.min(POPULAR_MAX_SIZE, value);
    }
}
