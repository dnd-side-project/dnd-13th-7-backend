package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.review.domain.BasicReview;
import com.moyeoit.domain.review.domain.BasicReviewDetail;
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
        String oneLineComment,
        String impressiveContentPreview,
        List<QAPreview> qaPreviews
) {

    public record QAPreview(
            String questionTitle,
            String answerValue
    ){}

    public static BasicReviewListResponse mapToDto(BasicReview review, List<BasicReviewDetail> details){

        List<QAPreview> qaPreviews = details.stream()
                .filter(p->p.getQuestion().getType().equals(QuestionType.MULTIPLE_CHOICE))
                .map(p->new QAPreview(p.getQuestion().getTitle(),p.getValue()))
                .toList();

        Optional<BasicReviewDetail> subjectiveDetail = details.stream()
                .filter(p -> p.getQuestion().getType().equals(QuestionType.SUBJECTIVE))
                .findFirst();

        String oneLineComment = "";
        String impressiveContentPreview = "";

        if (subjectiveDetail.isPresent()) {
            BasicReviewDetail detail = subjectiveDetail.get();
            oneLineComment = detail.getQuestion().getTitle();
            impressiveContentPreview = detail.getValue();
        }

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
                oneLineComment,
                impressiveContentPreview,
                qaPreviews
        );
    }
}
