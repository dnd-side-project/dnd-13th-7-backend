package com.moyeoit.context.review.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "블로그 후기 응답")
public class BlogReviewResponse {

    @Schema(description = "동아리 이름", example = "DND")
    private String clubName;

    @Schema(description = "기수", example = "13")
    private Integer generation;

    @Schema(description = "직무 이름", example = "백엔드 개발자")
    private String jobName;

    @Schema(description = "리뷰 제목", example = "활동 후기입니다.")
    private String title;

    @Schema(description = "블로그 URL", example = "https://velog.io/@user/review")
    private String url;

    @Schema(description = "블로그 썸네일 URL", example = "https://example.com")
    private String imageUrl;

    @Schema(description = "블로그 내용", example = "안드로이드 파트를 개발을 혼자 진행하였기에 여러 파트 사람들과 협업을 진개울가에 이르니, 며칠째 보이지 않던 소녀가 건너편 가에 앉아 물장난을 하고 있었다. 소년은 개울가에서 소녀를 보자 곧 윤 초시네 증손녀 딸이라는 걸 알 수 있었다. 소녀가 걸음을 멈추며...")
    private String description;

    @Schema(description = "블로그 이름", example = "바키의 블로그")
    private String blogName;

}
