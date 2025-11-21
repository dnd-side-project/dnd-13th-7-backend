package com.moyeoit.domain.review.presentation.request.comment;

import com.moyeoit.domain.review.domain.dto.ReviewCommentWithUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCommentResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createDate;
    private List<ReviewCommentResponse> children;
    private Boolean deleted;

    public static ReviewCommentResponse from(ReviewCommentWithUserDto dto) {
        return new ReviewCommentResponse(
                dto.getReviewCommentId(),
                dto.getNickname(),
                dto.getProfileImageUrl(),
                dto.getContent(),
                dto.getCreatedDate(),
                new ArrayList<>(),
                dto.getDeleted());
    }

}
