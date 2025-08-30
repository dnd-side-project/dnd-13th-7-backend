package com.moyeoit.domain.review.service;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.app_user.repository.JobRepository;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.review.controller.request.BasicReviewCreateRequest;
import com.moyeoit.domain.review.controller.request.answer.AnswerRequest;
import com.moyeoit.domain.review.controller.request.answer.MultipleChoiceAnswer;
import com.moyeoit.domain.review.controller.request.answer.SingleChoiceAnswer;
import com.moyeoit.domain.review.controller.request.answer.SubjectiveAnswer;
import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.Question;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.repository.BasicReviewDetailRepository;
import com.moyeoit.domain.review.repository.BasicReviewRepository;
import com.moyeoit.domain.review.repository.QuestionRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import com.moyeoit.global.exception.code.QuestionErrorCode;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicReviewService {

    private final AppUserRepository appUserRepository;
    private final ClubRepository clubRepository;
    private final JobRepository jobRepository;
    private final BasicReviewRepository basicReviewRepository;
    private final BasicReviewDetailRepository basicReviewDetailRepository;
    private final QuestionRepository questionRepository;


    @Transactional
    public void createBasicReview(BasicReviewCreateRequest request, Long userId) {
        // 유저 조회
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        // 동아리 조회
        Club club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));

        // 직군 조회
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_JOB));

        BasicReview basicReview = BasicReview.builder()
                .club(club)
                .cohort(request.getGeneration())
                .job(job)
                .rate(request.getRate())
                .reviewCategory(request.getReviewCategory())
                .resultType(request.getResultType())
                .user(user)
                .build();

        BasicReview savedReview = basicReviewRepository.save(basicReview);

        List<BasicReviewDetail> details = request.getQuestions().stream()
                .map(question -> createBasicDetail(savedReview, question, user))
                .toList();

        basicReviewDetailRepository.saveAll(details);
    }


    private BasicReviewDetail createBasicDetail(BasicReview review, AnswerRequest request, AppUser user) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new AppException(QuestionErrorCode.NOT_FOUND));

        if (request instanceof SubjectiveAnswer answer) { // 주관식 응답
            return BasicReviewDetail.builder()
                    .review(review)
                    .question(question)
                    .value(answer.getValue())
                    .answerType(AnswerType.TEXT)
                    .appUser(user)
                    .build();
        }

        if (request instanceof SingleChoiceAnswer answer) { // 객관식 단일 응답
            return BasicReviewDetail.builder()
                    .review(review)
                    .question(question)
                    .value(String.valueOf(answer.getValue()))
                    .answerType(AnswerType.INTEGER)
                    .appUser(user)
                    .build();
        }

        if (request instanceof MultipleChoiceAnswer answer) { // 객관식 다중 응답
            String value = answer.getValue().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            return BasicReviewDetail.builder()
                    .review(review)
                    .question(question)
                    .value(value)
                    .answerType(AnswerType.ARRAY_INTEGER)
                    .appUser(user)
                    .build();
        }

        throw new AppException(ReviewErrorCode.NOT_FOUND_TYPE);
    }

}
