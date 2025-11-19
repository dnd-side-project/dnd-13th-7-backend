package com.moyeoit.domain.review.infra;

import com.moyeoit.domain.club.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.domain.review.controller.response.QuestionElementResponse;
import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.domain.review.controller.response.v2.OriginalReviewAnswer;
import com.moyeoit.domain.review.controller.response.v2.OriginalReviewDetailView;
import com.moyeoit.domain.review.controller.response.v2.ReviewMetadata;
import com.moyeoit.domain.review.domain.enums.ReviewCategory;
import com.moyeoit.domain.review.domain.enums.ReviewResult;
import com.moyeoit.domain.review.domain.enums.ReviewSort;
import com.moyeoit.domain.review.presentation.request.ReviewSearchRequest;
import com.moyeoit.domain.review.presentation.response.ReviewSummaryResponse;
import com.moyeoit.domain.review.service.dto.ReviewOptionSummaryDto;
import com.moyeoit.domain.review.service.dto.ReviewQuestionSummaryDto;
import com.moyeoit.domain.user.service.dto.JobDto;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moyeoit.domain.club.entity.QClub.club;
import static com.moyeoit.domain.review.domain.model.QReview.review;
import static com.moyeoit.domain.review.domain.model.QReviewAnswer.reviewAnswer;
import static com.moyeoit.domain.review.domain.model.QReviewContentSummary.reviewContentSummary;
import static com.moyeoit.domain.review.domain.model.QReviewOption.reviewOption;
import static com.moyeoit.domain.review.domain.model.QReviewQuestion.reviewQuestion;
import static com.moyeoit.domain.user.domain.QJob.job;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QueryReviewRepository {

    private final JPAQueryFactory queryFactory;

    public OriginalReviewDetailView findReviewById(Long reviewId) {
        ReviewMetadata view = queryFactory
                .select(createReviewDetailView())
                .from(review)
                .leftJoin(job).on(review.jobId.eq(job.id))
                .leftJoin(club).on(review.clubId.eq(club.id))
                .where(review.id.eq(reviewId))
                .fetchOne();

        List<OriginalReviewAnswer> answers = queryFactory
                .select(createOriginalReviewAnswerView())
                .from(reviewAnswer)
                .leftJoin(reviewQuestion).on(reviewAnswer.reviewQuestionId.eq(reviewQuestion.id))
                .leftJoin(reviewOption).on(reviewQuestion.id.eq(reviewOption.question.id))
                .where(reviewAnswer.review.id.eq(reviewId))
                .transform(
                        groupBy(reviewAnswer.id).list(createOriginalReviewAnswerView())
                );

        return new OriginalReviewDetailView(
                view.getTitle(),
                view.getRate(),
                view.getResult(),
                view.getJob(),
                view.getClub(),
                view.getGeneration(),
                answers
        );
    }

    /**
     * 검색 Query
     *
     * @param request  검색 요청 Request
     * @param pageable 페이징 객체
     */
    public Page<ReviewSummaryResponse> search(ReviewSearchRequest request, Pageable pageable) {
        List<ReviewSummaryResponse> contents = queryFactory
                .select()
                .from(review)
                .leftJoin(club).on(review.clubId.eq(club.id))
                .leftJoin(job).on(review.jobId.eq(job.id))
                .leftJoin(reviewContentSummary).on(review.id.eq(reviewContentSummary.review.id))
                .where(eqTitle(request.getTitle()),
                        eqReviewCategory(request.getCategory()),
                        eqClubId(request.getClubId()),
                        eqGeneration(request.getGeneration()),
                        eqReviewResult(request.getResult()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(getOrderSpecifier(request.getSort()))
                .transform(
                        groupBy(review.id).list(createReviewSummary())
                );

        Long totalCount = queryFactory
                .select(review.count())
                .from(review)
                .where(eqReviewCategory(request.getCategory()),
                        eqClubId(request.getClubId()),
                        eqGeneration(request.getGeneration()),
                        eqReviewResult(request.getResult()))
                .fetchOne();

        return new PageImpl<>(contents, pageable, totalCount == null ? 0 : totalCount);
    }

    /**
     * Question ID 목록으로 Question과 Option 목록을 조회합니다.
     *
     * @param questionIds
     */
    public List<ReviewQuestionSummaryDto> findQuestionWithOptionsByQuestionIds(List<Long> questionIds) {
        return queryFactory
                .select(reviewQuestion, reviewOption)
                .from(reviewQuestion)
                .leftJoin(reviewOption).on(reviewOption.question.id.eq(reviewQuestion.id))
                .where(reviewQuestion.id.in(questionIds))
                .transform(
                        groupBy(reviewQuestion.id).list(
                                Projections.constructor(ReviewQuestionSummaryDto.class,
                                        reviewQuestion.id,
                                        reviewQuestion.title,
                                        list(Projections.constructor(ReviewOptionSummaryDto.class,
                                                reviewOption.title,
                                                reviewOption.description,
                                                reviewOption.sequence)
                                        ))
                        )
                );
    }

    public ReviewQuestionSummaryDto findQuestionWithOptionByQuestionId(Long questionId) {
        return queryFactory
                .select(Projections.constructor(
                        ReviewQuestionSummaryDto.class,
                        reviewQuestion.id,
                        reviewQuestion.title,
                        list(
                                Projections.constructor(
                                        ReviewOptionSummaryDto.class,
                                        reviewOption.title,
                                        reviewOption.description,
                                        reviewOption.sequence
                                )
                        )
                ))
                .from(reviewQuestion)
                .leftJoin(reviewOption).on(reviewOption.question.id.eq(reviewQuestion.id))
                .where(reviewQuestion.id.eq(questionId))
                .fetchOne();
    }

    public BooleanExpression eqTitle(String title) {
        if (StringUtils.hasText(title)) {
            return null;
        }
        return review.title.containsIgnoreCase(title);
    }

    public BooleanExpression eqReviewCategory(ReviewCategory category) {
        return category == null ? null : review.category.eq(category);
    }

    public BooleanExpression eqClubId(Long clubId) {
        return clubId == null ? null : review.clubId.eq(clubId);
    }

    public BooleanExpression eqGeneration(Integer generation) {
        return generation == null ? null : review.generation.eq(generation);
    }

    public BooleanExpression eqReviewResult(ReviewResult reviewResult) {
        return reviewResult == null ? null : review.result.eq(reviewResult);
    }


    /**
     * 리뷰 메타데이터 조회 Projection
     */
    private ConstructorExpression<OriginalReviewAnswer> createOriginalReviewAnswerView() {
        return Projections.constructor(OriginalReviewAnswer.class,
                reviewAnswer.id,
                Projections.constructor(QuestionResponse.class,
                        reviewQuestion.id,
                        reviewQuestion.title,
                        reviewQuestion.subtitle,
                        reviewQuestion.type,
                        list(Projections.constructor(QuestionElementResponse.class,
                                reviewOption.id,
                                reviewOption.title,
                                reviewOption.sequence
                        ).skipNulls())
                ),
                reviewAnswer.value,
                reviewAnswer.valueType);
    }

    /**
     * 리뷰 내용 조회 Projection
     */
    public ConstructorExpression<ReviewMetadata> createReviewDetailView() {
        return Projections.constructor(ReviewMetadata.class,
                review.title,
                review.rate,
                review.result,
                Projections.constructor(JobDto.class,
                        job.id,
                        job.name,
                        job.engName),
                Projections.constructor(ClubWithNameAndImageUrlDto.class,
                        club.name,
                        club.clubProfile.imageUrl),
                review.generation
        );
    }

    /**
     * 리뷰 Summary Projection
     */
    public ConstructorExpression<ReviewSummaryResponse> createReviewSummary() {
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

    /**
     * 정렬 순서
     */
    public OrderSpecifier<?>[] getOrderSpecifier(ReviewSort sort) {
        if (ReviewSort.LATEST.equals(sort))
            return new OrderSpecifier[]{review.createdDate.desc()};
        if (ReviewSort.POPULAR.equals(sort))
            return new OrderSpecifier[]{review.likeCount.desc(), review.createdDate.desc()};
        return new OrderSpecifier[]{review.createdDate.desc()};
    }

}
