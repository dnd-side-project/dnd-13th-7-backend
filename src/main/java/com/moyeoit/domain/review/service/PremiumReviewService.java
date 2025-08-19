package com.moyeoit.domain.review.service;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.app_user.repository.JobRepository;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.review.controller.request.AnswerRequest;
import com.moyeoit.domain.review.controller.request.MultipleChoiceAnswer;
import com.moyeoit.domain.review.controller.request.PremiumReviewCreateRequest;
import com.moyeoit.domain.review.controller.request.SubjectiveAnswer;
import com.moyeoit.domain.review.domain.PremiumReview;
import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.domain.Question;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.review.repository.PremiumReviewDetailRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.domain.review.repository.QuestionRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import com.moyeoit.global.exception.code.QuestionErrorCode;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PremiumReviewService {

    private final AppUserRepository appUserRepository;
    private final ClubRepository clubRepository;
    private final JobRepository jobRepository;

    private final PremiumReviewRepository premiumReviewRepository;
    private final PremiumReviewDetailRepository premiumReviewDetailRepository;

    private final QuestionRepository questionRepository;

    @Transactional
    public void createPremiumReview(PremiumReviewCreateRequest request, Long userId) {
        // 유저 조회
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        // 동아리 조회
        Club club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new AppException(ClubErrorCode.NOT_FOUND));

        // 직군 조회
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_JOB));

        PremiumReview review = PremiumReview.builder()
                .club(club)
                .cohort(request.getGeneration())
                .job(job)
                .user(user)
                .imageUrl(request.getImageUrl())
                .title(request.getTitle())
                .resultType(request.getResultType())
                .reviewCategory(request.getReviewCategory())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        PremiumReview savedReview = premiumReviewRepository.save(review);
        List<PremiumReviewDetail> details = request.getQuestions().stream()
                .map(question -> createPremiumDetail(savedReview, question, user))
                .toList();

        premiumReviewDetailRepository.saveAll(details);
    }

    private PremiumReviewDetail createPremiumDetail(PremiumReview review, AnswerRequest request, AppUser user) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new AppException(QuestionErrorCode.NOT_FOUND));

        if (request instanceof MultipleChoiceAnswer answer) { // 객관식 처리
            return PremiumReviewDetail.builder()
                    .review(review)
                    .question(question)
                    .value(String.valueOf(answer.getValue()))
                    .answerType(AnswerType.INTEGER)
                    .appUser(user)
                    .build();
        }

        if (request instanceof SubjectiveAnswer answer) { // 주관식 처리
            return PremiumReviewDetail.builder()
                    .review(review)
                    .question(question)
                    .value(answer.getValue())
                    .answerType(AnswerType.TEXT)
                    .appUser(user)
                    .build();
        }

        throw new AppException(ReviewErrorCode.NOT_FOUND_TYPE);
    }

}
