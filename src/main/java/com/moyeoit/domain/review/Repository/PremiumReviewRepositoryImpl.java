package com.moyeoit.domain.review.Repository;


import static com.moyeoit.domain.app_user.domain.QAppUser.appUser;
import static com.moyeoit.domain.app_user.domain.QJob.job;
import static com.moyeoit.domain.club.entity.QClub.club;
import static com.moyeoit.domain.review.domain.QPremiumReview.premiumReview;

import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.domain.enums.Result;
import com.moyeoit.domain.review.domain.enums.ReviewType;
import com.moyeoit.domain.review.dto.QReviewQueryDto_PremiumReviewInfo;
import com.moyeoit.domain.review.dto.ReviewQueryDto.PremiumReviewInfo;
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

@Repository
@RequiredArgsConstructor
public class PremiumReviewRepositoryImpl implements PremiumReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PremiumReviewInfo> findPremiumReviewByRequest(ReviewPagingRequest request, Pageable pageable) {
        BooleanExpression[] conditions = {
                clubIdEq(request.getClubId()),
                partEq(request.getPart()),
                resultEq(request.getResult()),
                reviewTypeEq(request.getReviewType()),
                isRecruitingEq(request.getIsRecruiting())
        };

        List<PremiumReviewInfo> content = queryFactory
                .select(new QReviewQueryDto_PremiumReviewInfo(
                        premiumReview.id,
                        premiumReview.title,
                        premiumReview.imageUrl,
                        club.name,
                        premiumReview.cohort,
                        job.name
                ))
                .from(premiumReview)
                .join(premiumReview.user, appUser)
                .join(premiumReview.club, club)
                .join(premiumReview.job, job)
                .where(conditions)
                .orderBy(getOrderSpecifier(request.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // countQuery 준비
        JPAQuery<Long> countQuery = queryFactory
                .select(premiumReview.count())
                .from(premiumReview)
                .join(premiumReview.club, club)
                .join(premiumReview.job, job)
                .where(conditions);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression reviewTypeEq(String reviewType) {
        return StringUtils.hasText(reviewType) ? premiumReview.reviewType.eq(ReviewType.valueOf(reviewType)) : null;
    }

    private BooleanExpression clubIdEq(String clubId) {
        return StringUtils.hasText(clubId) ? club.id.eq(Long.valueOf(clubId)) : null;
    }


    private BooleanExpression partEq(String part) {
        return StringUtils.hasText(part) ? job.name.eq(part) : null;
    }


    private BooleanExpression resultEq(String result) {
        return StringUtils.hasText(result) ? premiumReview.result.eq(Result.valueOf(result)) : null;
    }

    private BooleanExpression isRecruitingEq(Boolean isRecruiting){
        return isRecruiting == null ? null : premiumReview.club.recruiting.eq(isRecruiting);
    }


    private OrderSpecifier<?> getOrderSpecifier(String sort){
//        if(ReviewSort.fromString(sort)==ReviewSort.인기순){
//            todo: 좋아요 로직 추가후 premiumReview.likeCount.desc(); 예정
//        }
        return premiumReview.createDate.desc();
    }
}
