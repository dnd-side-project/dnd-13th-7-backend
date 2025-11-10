package com.moyeoit.domain.review.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoit.domain.club.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.controller.response.QuestionElementResponse;
import com.moyeoit.domain.review.controller.response.QuestionResponse;
import com.moyeoit.domain.review.controller.response.v2.*;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import com.moyeoit.domain.user.service.dto.JobDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

import static com.moyeoit.domain.club.entity.QClub.club;
import static com.moyeoit.domain.review.domain.v2.QReview.review;
import static com.moyeoit.domain.review.domain.v2.QReviewAnswer.reviewAnswer;
import static com.moyeoit.domain.review.domain.v2.QReviewOption.reviewOption;
import static com.moyeoit.domain.review.domain.v2.QReviewQuestion.reviewQuestion;
import static com.moyeoit.domain.user.domain.QJob.job;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.list;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl {

    private final JPAQueryFactory queryFactory;
    private final ObjectMapper objectMapper;

    public OriginalReviewDetailView findReviewById(Long reviewId)  {
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
                .fetch();

        return new OriginalReviewDetailView(view.getJob(), view.getClub(), view.getGeneration(), answers);
    }

//    public ConstructorExpression<ReviewSummary> createReviewSummaryView() {
//        return Projections.constructor(ReviewSummary.class,
//                Projections.constructor(ClubWithNameAndImageUrlDto.class,
//                        club.name,
//                        club.clubProfile.imageUrl
//                ),
//                review.generation,
//                Projections.constructor(JobDto.class,
//                        job.id,
//                        job.name,
//                        job.engName
//                ),
//                list(Projections.constructor())
//                )
//    }

    public List<ReviewSummary> search(ReviewPagingRequest request, Pageable pageable) {

        // 1) Review 기본 정보만 조회
        List<Tuple> reviewTuples = queryFactory
                .select(
                        review.id,
                        club.name,
                        club.clubProfile.imageUrl,
                        review.generation,
                        job.id,
                        job.name,
                        job.engName
                )
                .from(review)
                .leftJoin(job).on(review.jobId.eq(job.id))
                .leftJoin(club).on(review.clubId.eq(club.id))
                .fetch();

        if (reviewTuples.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> reviewIds = reviewTuples.stream()
                .map(t -> t.get(review.id))
                .collect(Collectors.toSet());

        // 2) Answer 정보 (SINGLE_CHOICE만)
        Map<Long, List<ReviewAnswerSummary>> answerMap = queryFactory
                .select(
                        reviewAnswer.review.id,
                        reviewQuestion.subtitle,
                        reviewOption.title
                )
                .from(reviewAnswer)
                .leftJoin(reviewQuestion).on(reviewAnswer.reviewQuestionId.eq(reviewQuestion.id))
                .leftJoin(reviewOption).on(
                        reviewOption.question.id.eq(reviewQuestion.id)
                                .and(reviewOption.question.type.eq(QuestionType.SINGLE_CHOICE))
                                .and(reviewOption.sequence.stringValue().eq(reviewAnswer.value))
                )
                .where(
                        reviewAnswer.review.id.in(reviewIds),
                        reviewAnswer.valueType.eq(AnswerType.INTEGER)
                )
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(reviewAnswer.review.id),
                        Collectors.mapping(
                                tuple -> new ReviewAnswerSummary(
                                        tuple.get(reviewQuestion.subtitle),
                                        tuple.get(reviewOption.title)
                                ),
                                Collectors.toList()
                        )
                ));

        // 3) 조립
        return reviewTuples.stream()
                .map(tuple -> new ReviewSummary(
                        new ClubWithNameAndImageUrlDto(
                                tuple.get(club.name),
                                tuple.get(club.clubProfile.imageUrl)
                        ),
                        tuple.get(review.generation),
                        new JobDto(
                                tuple.get(job.id),
                                tuple.get(job.name),
                                tuple.get(job.engName)
                        ),
                        answerMap.getOrDefault(tuple.get(review.id), Collections.emptyList())
                ))
                .collect(Collectors.toList());
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
                        ))
                ),
                reviewAnswer.value,
                reviewAnswer.valueType);
    }

    /**
     * 리뷰 내용 조회 Projection
     */
    public ConstructorExpression<ReviewMetadata> createReviewDetailView() {
        return Projections.constructor(ReviewMetadata.class,
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

}
