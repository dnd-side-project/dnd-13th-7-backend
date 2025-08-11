package com.moyeoit.domain.club.repository;


import com.moyeoit.domain.club.dto.ClubPagingRequest;
import com.moyeoit.domain.club.entity.Club;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.moyeoit.domain.club.entity.QClub.club;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Club> findClubByRequest(ClubPagingRequest request, Pageable pageable) {
        List<Club> content = queryFactory
                .selectFrom(club)
                .where(
                        eqField(request.getField()),
                        eqWay(request.getWay()),
                        eqPart(request.getPart()),
                        eqTarget(request.getTarget())
                )
                .orderBy(getOrderSpecifier(request.getSort(),pageable)) // 정렬 조건
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(club.count())
                .from(club)
                .where(
                        eqField(request.getField()),
                        eqWay(request.getWay()),
                        eqPart(request.getPart()),
                        eqTarget(request.getTarget())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqField(String field){
        return StringUtils.hasText(field) ? club.position.eq(field) : null;
    }
    private BooleanExpression eqWay(String way){
        if (!StringUtils.hasText(way)) {
            return null;
        }
        return "온라인".equals(way) ? club.online.isNotNull() : club.offline.isNotNull();
    }
    private BooleanExpression eqPart(List<String> parts){
        if (parts == null || parts.isEmpty()) {
            return null;
        }

        return club.recruitment.recruitmentParts.any().parts.in(parts);
    }
    private BooleanExpression eqTarget(List<String> targets) {
        if (targets == null || targets.isEmpty()) {
            return null;
        }

        BooleanExpression condition = null;

        if (targets.contains("대학생")) {
            condition = club.targetUniversity.isTrue();
        }
        if (targets.contains("직장인")) {
            BooleanExpression workerCond = club.targetWorker.isTrue();
            condition = (condition == null) ? workerCond : condition.or(workerCond);
        }

        return condition;
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort,Pageable pageable){
        if(StringUtils.hasText(sort)){
//            추후 구독 추가후 구현 예정
//            if("인기순".equals(sort)){
//                return new OrderSpecifier[]{}
//            }
            return club.establishment.desc();
        }
        return club.id.desc();
    }


}
