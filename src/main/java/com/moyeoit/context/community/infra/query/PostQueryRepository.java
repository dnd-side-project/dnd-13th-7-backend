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

    public Page<PostCardResponse> findFeed(CommunityCategoryType category, Pageable pageable) {
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
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        eqCategoryName(category),
                        filterHotPost(category)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    public Page<PopularPostResponse> findPopular(Pageable pageable) {
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
                        post.likeCount.goe(10))
                .orderBy(post.createdAt.desc(),post.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        post.isDeleted.isFalse(),
                        post.likeCount.goe(10));

        return PageableExecutionUtils.getPage(content, pageable , countQuery::fetchOne);
    }

    public PostDetailInfoResponse findPostDetailInfo(Long postId, Long userId) {
        PostDetailInfoResponse result = queryFactory
                .select(new QPostDetailInfoResponse(
                        post.category.name,
                        post.commentCount,
                        post.content,
                        post.createdAt,
                        Expressions.constant(new ArrayList<>()),
                        post.likeCount.goe(10),
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
        return post.category.name.eq(category.getDisplayName());
    }

    private BooleanExpression filterHotPost(CommunityCategoryType category) {
        if (category != null && category.isPopular()) {
            return post.likeCount.goe(10);
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
}
