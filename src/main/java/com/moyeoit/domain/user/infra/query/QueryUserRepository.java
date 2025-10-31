package com.moyeoit.domain.user.infra.query;

import com.moyeoit.domain.user.domain.QJob;
import com.moyeoit.domain.user.domain.QUser;
import com.moyeoit.domain.user.service.dto.JobDto;
import com.moyeoit.domain.user.service.dto.UserProfileResponse;
import com.moyeoit.domain.user.service.dto.UserWithJobResult;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.moyeoit.domain.user.domain.QJob.job;
import static com.moyeoit.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class QueryUserRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<UserProfileResponse> findUserWithJob(Long userId) {
        UserProfileResponse userProfileResponse = queryFactory.select(Projections.constructor(
                UserProfileResponse.class,
                user.id,
                user.name,
                user.email,
                user.nickname,
                user.profileImageUrl,
                Projections.constructor(JobDto.class,
                        job.id,
                        job.name,
                        job.engName
                ),
                user.provider,
                user.active))
                .from(user)
                .leftJoin(job).on(user.jobId.eq(job.id))
                .where(user.id.eq(userId))
                .fetchFirst();

        return Optional.ofNullable(userProfileResponse);
    }
}
