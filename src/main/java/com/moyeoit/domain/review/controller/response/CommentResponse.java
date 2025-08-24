package com.moyeoit.domain.review.controller.response;

import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.review.domain.PremiumReviewComment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private Long premiumReviewId;
    private String content;
    private AppUserDto appUser;
    private List<CommentResponse> childComments;

    public static CommentResponse from(PremiumReviewComment comment) {
        return new CommentResponse(comment.getId(),
                comment.getPremiumReview().getId(),
                comment.getContent(),
                AppUserDto.of(comment.getAppUser()),
                List.of()
        );
    }

}