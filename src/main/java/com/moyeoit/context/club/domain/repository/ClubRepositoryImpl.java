package com.moyeoit.context.club.domain.repository;


import static com.moyeoit.context.club.domain.entity.QClub.club;
import static com.moyeoit.context.deprecated.bookmark.infra.entity.QBookmarkEntity.bookmarkEntity;

import com.moyeoit.context.club.presentation.request.ClubPagingRequest;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.infra.query.ClubActivityType;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Club> findClubByRequest(ClubPagingRequest request, Pageable pageable, Long userId) {
        BooleanExpression[] conditions = {
                eqField(request.getField()),
                eqWay(request.getWay()),
                eqPart(request.getPart()),
                eqTarget(request.getTarget())
        };

        JPAQuery<Club> contentQuery = queryFactory
                .selectFrom(club)
                .where(conditions);

        if (userId != null) {
            contentQuery.leftJoin(bookmarkEntity)
                    .on(bookmarkEntity.targetId.eq(club.id),
                            bookmarkEntity.userId.eq(userId),
                            bookmarkEntity.type.eq(BookmarkType.CLUB),
                            bookmarkEntity.isActive.isTrue());
        }

        List<Club> content = contentQuery
                .orderBy(getOrderSpecifiers(userId, request.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(club.count())
                .from(club)
                .where(conditions);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqField(String field){
        return StringUtils.hasText(field) ? club.positions.any().name.eq(field) : null;
    }
    private BooleanExpression eqWay(String way){
        if (!StringUtils.hasText(way)) {
            return null;
        }
        ClubActivityType method = ClubActivityType.fromString(way);

        if (method==null) {
            return null;
        }

        return method== ClubActivityType.ONLINE ? club.ClubActivityMethod.online.isNotNull() : club.ClubActivityMethod.offline.isNotNull();
    }
    private BooleanExpression eqPart(String parts){
//        if (parts == null || parts.isEmpty()) {
//            return null;
//        }
//        List<String> part = Arrays.stream(parts.split(","))
//                .map(String::trim)
//                .toList();
//
//        return club.recruitment.clubRecruitmentParts.any().job.name.in(part);
        return null;
    }
    private BooleanExpression eqTarget(String targets) {
        if (targets == null || targets.isEmpty()) {
            return null;
        }

        List<String> target = Arrays.stream(targets.split(","))
                .map(String::trim)
                .toList();

//        return club.target.any().targetName.in(target);
        return null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Long userId, String sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (userId != null) {
            orderSpecifiers.add(new OrderSpecifier<>(
                    Order.DESC,
                    new CaseBuilder()
                            .when(bookmarkEntity.id.isNotNull())
                            .then(1)
                            .otherwise(0)
            ));
        }

        orderSpecifiers.add(new OrderSpecifier<>(
                Order.DESC,
                new CaseBuilder()
                        .when(club.recruiting.isTrue())
                        .then(1)
                        .otherwise(0)
        ));

        orderSpecifiers.add(getOrderSpecifier(sort));
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort){
        if(StringUtils.hasText(sort)){
            if("인기순".equals(sort)){
               return club.subscribeCount.desc();
            }
            if ("최신순".equals(sort)) {
                return club.id.desc();
            }
            if ("이름순".equals(sort)) {
                return club.name.asc();
            }
            return club.name.asc();
        }
        return club.id.desc();
    }

}
