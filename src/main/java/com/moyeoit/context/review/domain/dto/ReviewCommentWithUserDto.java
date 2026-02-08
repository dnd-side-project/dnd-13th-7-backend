package com.moyeoit.context.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCommentWithUserDto {

    private Long reviewCommentId;
    private String nickname;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createdDate;
    private Long parentId;
    private Boolean deleted;

}
