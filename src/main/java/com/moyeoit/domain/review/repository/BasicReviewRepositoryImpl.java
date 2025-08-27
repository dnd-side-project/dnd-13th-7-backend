package com.moyeoit.domain.review.repository;

import static com.moyeoit.domain.app_user.domain.QAppUser.appUser;
import static com.moyeoit.domain.app_user.domain.QJob.job;
import static com.moyeoit.domain.club.entity.QClub.club;
import static com.moyeoit.domain.review.domain.QBasicReview.basicReview;
import static com.moyeoit.domain.review.domain.QBasicReviewDetail.basicReviewDetail;
import static com.moyeoit.domain.review.domain.QQuestion.question;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.ResultType;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.controller.response.BasicReviewListResponse;
import com.moyeoit.domain.review.domain.ReviewType;
import com.moyeoit.domain.review.domain.enums.ReviewSort;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class BasicReviewRepositoryImpl implements BasicReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BasicReviewListResponse> findBasicReviewByRequest(ReviewPagingRequest request, Pageable pageable) {
        BooleanExpression[] conditions = {
                clubIdEq(request.getClubId()),
                partEq(request.getPart()),
                resultEq(request.getResult()),
                reviewTypeEq(request.getReviewType()),
                isRecruitingEq(request.getIsRecruiting())
        };
        List<BasicReview> reviews = queryFactory
                .selectFrom(basicReview)
                .join(basicReview.user, appUser).fetchJoin()
                .join(basicReview.club, club).fetchJoin()
                .join(basicReview.job, job).fetchJoin()
                .where(conditions)
                .orderBy(getOrderSpecifier(request.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(basicReview.count())
                .from(basicReview)
                .join(basicReview.club, club)
                .where(conditions);

        List<Long> reviewIds = reviews.stream()
                .map(BasicReview::getId)
                .toList();

        List<BasicReviewDetail> details = queryFactory
                .selectFrom(basicReviewDetail)
                .join(basicReviewDetail.question, question).fetchJoin()
                .where(basicReviewDetail.review.id.in(reviewIds))
                .fetch();

        Map<Long, List<BasicReviewDetail>> detailMap = details.stream()
                .collect(Collectors.groupingBy(d -> d.getReview().getId()));

        return PageableExecutionUtils.getPage(
                reviews.stream()
                        .map(review -> BasicReviewListResponse.mapToDto(
                                review,
                                detailMap.getOrDefault(review.getId(), List.of())
                        ))
                        .toList(),
                pageable,
                countQuery::fetchOne);
    }

    private BooleanExpression clubIdEq(String clubId) {
        return StringUtils.hasText(clubId) ? club.id.eq(Long.valueOf(clubId)) : null;
    }

    private BooleanExpression partEq(String part) {
        return StringUtils.hasText(part) ? basicReview.job.name.eq(part) : null;
    }

    private BooleanExpression resultEq(String result) {
        return result != null ? basicReview.resultType.eq(ResultType.valueOf(result)) : null;
    }

    private BooleanExpression reviewTypeEq(String reviewType) {
        return reviewType != null ? basicReview.reviewCategory.eq(ReviewCategory.valueOf(reviewType)) : null;
    }

    private BooleanExpression isRecruitingEq(Boolean isRecruiting) {
        return isRecruiting != null ? basicReview.club.recruiting.eq(isRecruiting) : null;
    }


    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        if(ReviewSort.fromString(sort) == ReviewSort.인기순){
            return basicReview.likeCount.desc();
        }
        return basicReview.createDate.desc();
    }

}
