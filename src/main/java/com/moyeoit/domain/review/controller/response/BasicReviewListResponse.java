package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
import com.moyeoit.domain.review.domain.QuestionElement;
import com.moyeoit.domain.review.domain.enums.QuestionType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public record BasicReviewListResponse(
        Long reviewId,
        String profileImgUrl, //추후 추가
        String nickname,
        String clubName,
        String cohort,
        String part,
        String position,
        Double rate,
        String reviewCategory,
        String createdAt,
        Integer likeCount,
        List< QuestionAnswerSet> qaPreviews
) {

    public record  QuestionAnswerSet(
            String questionTitle,
            String answerValue
    ){}

    public static BasicReviewListResponse mapToDto(BasicReview review, List<BasicReviewDetail> details) {

        List<QuestionAnswerSet> questionAnswerSets = details.stream()
                .map(detail -> {
                    String questionTitle = detail.getQuestion().getTitle();
                    String answerValue;

                    switch (detail.getQuestion().getType()) {
                        case SINGLE_CHOICE:
                        case MULTIPLE_CHOICE:
                            List<QuestionElement> elements = detail.getQuestion().getQuestionElements();
                            String selectedElementId = detail.getValue();

                            answerValue = elements.stream()
                                    .filter(element -> String.valueOf(element.getId()).equals(selectedElementId))
                                    .findFirst()
                                    .map(QuestionElement::getElementTitle)
                                    .orElse(selectedElementId);
                            break;

                        default:
                            answerValue = detail.getValue();
                            break;
                    }

                    return new QuestionAnswerSet(questionTitle, answerValue);
                })
                .toList();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String jobFamily = switch (review.getJob().getName()){
            case String s when s.contains("개발자") -> "개발";
            case String s when s.contains("디자이너") -> "디자인";
            case String s when s.contains("PM") -> "기획";
            default -> "기타";
        };

        return new BasicReviewListResponse(
                review.getId(),
                review.getUser().getProfileImageUrl(),
                review.getUser().getNickname(),
                review.getClub().getName(),
                review.getCohort().toString(),
                review.getJob().getName(),
                jobFamily,
                review.getRate(),
                review.getReviewCategory().name,
                review.getCreateDate().format(formatter),
                review.getLikeCount(),
                questionAnswerSets
        );
    }
}
