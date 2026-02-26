package com.moyeoit.context.deprecated.review.presentation.request.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCommentCreateRequest {

    private Long reviewId;
    private String content;
    private Long parentCommentId;

}