package com.moyeoit.context.user.infra.query;

import com.moyeoit.context.user.controller.response.UserActivityResponse;
import com.moyeoit.context.user.domain.QUserActivity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.moyeoit.context.club.domain.entity.QClub.club;
import static com.moyeoit.context.user.domain.QJob.job;
import static com.moyeoit.context.user.domain.QUserActivity.userActivity;

@Repository
@RequiredArgsConstructor
public class QueryUserActivityRepository {

    private final JPAQueryFactory queryFactory;

    public UserActivityResponse getUserActivity(Long userId) {
        return queryFactory.select(Projections.constructor(UserActivityResponse.class,
            userActivity.id,
            userActivity.userId,
            club.name,
            job.name,
            userActivity.generation,
            userActivity.startDate,
            userActivity.endDate
        ))
        .from(userActivity)
        .leftJoin(club).on(userActivity.clubId.eq(club.id))
        .leftJoin(job).on(userActivity.jobId.eq(job.id))
        .where(userActivity.userId.eq(userId))
        .fetchFirst();
    }





}
