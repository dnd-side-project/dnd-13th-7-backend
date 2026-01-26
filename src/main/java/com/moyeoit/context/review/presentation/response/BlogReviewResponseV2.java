package com.moyeoit.context.review.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "블로그 후기 응답")
public class BlogReviewResponseV2 {

    private Long id;
    private String clubName;
    private String jobName;
    private String title;
    private String blogName;
    private Integer generation;
    private String blogUrl;;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}