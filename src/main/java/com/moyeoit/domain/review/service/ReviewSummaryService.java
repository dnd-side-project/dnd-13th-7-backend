package com.moyeoit.domain.review.service;

import com.moyeoit.domain.review.controller.request.v2.MultipleChoiceAnswerV2;
import com.moyeoit.domain.review.controller.request.v2.ReviewAnswerCreateRequest;
import com.moyeoit.domain.review.controller.request.v2.SingleChoiceAnswerV2;
import com.moyeoit.domain.review.controller.request.v2.SingleSubjectiveAnswer;
import com.moyeoit.domain.review.domain.ReviewContentSummary;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import com.moyeoit.domain.review.domain.v2.Review;
import com.moyeoit.domain.review.infra.QueryReviewRepository;
import com.moyeoit.domain.review.repository.ReviewSummaryRepository;
import com.moyeoit.domain.review.service.dto.ReviewOptionSummaryDto;
import com.moyeoit.domain.review.service.dto.ReviewQuestionSummaryDto;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewSummaryService {

    private final QueryReviewRepository queryReviewRepository;
    private final ReviewSummaryRepository reviewSummaryRepository;

    public Long createReviewSummary(Review originalReview, List<ReviewAnswerCreateRequest> requests) {

        // 1) 객관식 답변 변환

        // 1-1) 객관식 질문/답변만 추출
        List<ReviewAnswerCreateRequest> choiceQnas = requests.stream()
                .filter(req -> req.getQuestionType().isChoice())
                .toList();

        // 1-2) 객관식 질문/답변과 연관된 Question ID를 추출
        List<Long> choiceQuestionIds = choiceQnas.stream()
                .map(ReviewAnswerCreateRequest::getQuestionId)
                .toList();

        // 1-3) 객관식 질문/답변과 연관된 Question ID 기반의 Question 및 Option 데이터 조회 후 유저의 선택과 맞는 Option의 title 추출
        List<ReviewQuestionSummaryDto> reviewQuestionSummariesOfChoice = queryReviewRepository.findQuestionWithOptionsByQuestionIds(choiceQuestionIds);
        List<String> choiceSummaries = requests.stream()
                .map(req -> resolveChoiceSummary(reviewQuestionSummariesOfChoice, req))
                .flatMap(List::stream)
                .toList();


        // 2) 주관식 대표 답변 변환
        // TODO: 한줄평만 가지고 오는건 확장적이지 않음. 코드를 부여해서 그 코드를 지정하는 방법으로 해야함.
        // 2-1) 주관식 답변인 것들 중 처음으로 오는 주관식 답변을 조회합니다.

        SingleSubjectiveAnswer subjectiveQna = (SingleSubjectiveAnswer) requests.stream()
                .filter(req -> req.getQuestionType() == QuestionType.SINGLE_SUBJECTIVE || req instanceof SingleSubjectiveAnswer)
                .min(Comparator.comparing(ReviewAnswerCreateRequest::getSequence))
                .orElseThrow(() -> new AppException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST));

        // 2-2) 해당 답변의 Question ID로 Question.title을 가져와서 '질문|v|답변' 으로 변환합니다.
        ReviewQuestionSummaryDto reviewQuestionSummaryOfSubjective = queryReviewRepository.findQuestionWithOptionByQuestionId(subjectiveQna.getQuestionId());
        String subjectiveSummary = reviewQuestionSummaryOfSubjective.getTitle() + "|v|" + subjectiveQna.getValue();

        // 3) 리뷰 요약 데이터를 생성합니다.
        ReviewContentSummary reviewContentSummary = ReviewContentSummary.builder()
                .review(originalReview)
                .choiceSummary(choiceSummaries)
                .subjectiveSummary(subjectiveSummary)
                .build();

        reviewSummaryRepository.save(reviewContentSummary);
        return reviewContentSummary.getId();
    }


    /**
     * 질문과 답항을 요약한 객체에서 유저 답변과 적합한 객체를 찾고, 타입에 따라 객관식 질문/답변 요약 데이터를 생성합니다.
     *
     * @param reviewQuestionSummaries 객관식 질문과 답변을 요약한 객체 배열
     * @param reviewAnswer            유저가 작성한 객관식 답변
     * @return { "가장 좋아하는 과일은?" , "사과" } 혹은 { "가장 좋아하는 과일은?", "사과,바나나" }
     */
    private List<String> resolveChoiceSummary(List<ReviewQuestionSummaryDto> reviewQuestionSummaries, ReviewAnswerCreateRequest reviewAnswer) {
        ReviewQuestionSummaryDto summaryDto = reviewQuestionSummaries.stream()
                .filter(req -> req.getQuestionId().equals(reviewAnswer.getQuestionId()))
                .findAny()
                .orElseThrow(() -> new AppException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST));

        if (QuestionType.SINGLE_CHOICE.equals(reviewAnswer.getQuestionType())) {
            SingleChoiceAnswerV2 answer = (SingleChoiceAnswerV2) reviewAnswer;
            return resolveSingleChoiceSummary(summaryDto, answer.getValue());
        }

        if (QuestionType.MULTIPLE_CHOICE.equals(reviewAnswer.getQuestionType())) {
            MultipleChoiceAnswerV2 answer = (MultipleChoiceAnswerV2) reviewAnswer;
            return resolveMultipleChoiceSummary(summaryDto, answer.getValue());
        }

        throw new AppException((ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST));
    }

    /**
     * 단일 객관식 질문과 이에 유저가 고른 답항의 제목을 요약한 데이터를 생성합니다.
     *
     * @param summary 질문과 답변을 요약한 객체
     * @param value   유저가 고른 번호 (sequence)
     * @return { "가장 좋아하는 과일은?" , "사과" }
     */
    private List<String> resolveSingleChoiceSummary(ReviewQuestionSummaryDto summary,
                                                    Integer value) {
        List<String> summaryResult = new ArrayList<>();
        summaryResult.add(summary.getTitle());

        List<ReviewOptionSummaryDto> optionSummaries = summary.getOptions();
        String answer = optionSummaries.stream()
                .filter(option -> value.equals(option.getSequence()))
                .map(ReviewOptionSummaryDto::getTitle)
                .collect(Collectors.joining());
        summaryResult.add(answer);
        return summaryResult;
    }

    /**
     * 다중 객관식 질문과 이에 유저가 고른 답항의 제목을 요약한 데이터를 생성합니다.
     *
     * @param summary 질문과 답변을 요약한 객체
     * @param values  유저가 고른 번호 목록 (sequence)
     * @return { "가장 좋아하는 과일은?", "사과,바나나" }
     */
    private List<String> resolveMultipleChoiceSummary(ReviewQuestionSummaryDto summary,
                                                      List<Integer> values) {
        List<String> summaryResult = new ArrayList<>();
        summaryResult.add(summary.getTitle());

        List<ReviewOptionSummaryDto> optionSummaries = summary.getOptions();
        String answers = optionSummaries.stream()
                .filter(option -> values.contains(option.getSequence()))
                .map(ReviewOptionSummaryDto::getTitle)
                .collect(Collectors.joining(","));
        summaryResult.add(answers);

        return summaryResult;
    }

}
