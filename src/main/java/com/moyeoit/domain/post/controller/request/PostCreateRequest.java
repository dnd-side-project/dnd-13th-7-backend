package com.moyeoit.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "게시글 생성 요청")
public class PostCreateRequest {

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;
    @Schema(description = "게시글 제목", example = "오늘 저녁 치킨 같이 드실 분?")
    private String title;
    @Schema(description = "게시글 내용", example = "BHC 뿌링클 치킨 기프티콘이 있는데 오늘 저녁에 같이 드실 분 구합니다.")
    private String content;
    @Schema(description = "게시글 이미지 목록")
    List<PostCreateImage> images;

    @Getter
    @Schema(description = "게시글 생성 이미지")
    public static class PostCreateImage{
        @Schema(description = "이미지 URL", example = "https://moyeoit.s3.ap-northeast-2.amazonaws.com/image.jpg")
        private String url;
        @Schema(description = "이미지 순서", example = "1")
        private Integer orderIndex;
    }
}
