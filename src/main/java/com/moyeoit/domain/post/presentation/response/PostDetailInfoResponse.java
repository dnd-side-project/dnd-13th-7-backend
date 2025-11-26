package com.moyeoit.domain.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "게시글 상세 정보 응답")
public class PostDetailInfoResponse {
    @Schema(description = "인기 게시글 여부", example = "true")
    boolean isHotPost;
    @Schema(description = "카테고리 이름", example = "자유")
    String categoryName;
    @Schema(description = "게시글 제목", example = "오늘 저녁 치킨 같이 드실 분?")
    String title;
    @Schema(description = "작성자 닉네임", example = "치킨마니아")
    String nickname;
    @Schema(description = "게시글 내용", example = "BHC 뿌링클 치킨 기프티콘이 있는데 오늘 저녁에 같이 드실 분 구합니다.")
    String content;
    @Schema(description = "이미지 URL 목록", example = "[\"https://moyeoit.s3.ap-northeast-2.amazonaws.com/image1.jpg\", \"https://moyeoit.s3.ap-northeast-2.amazonaws.com/image2.jpg\"]")
    List<String> image_url;
    @Schema(description = "작성일")
    LocalDateTime create_at;
    @Schema(description = "조회수", example = "100")
    int view_count;
    @Schema(description = "좋아요 수", example = "10")
    int like_count;
    @Schema(description = "댓글 수", example = "5")
    int comment_count;
    @Schema(description = "좋아요 여부", example = "false")
    boolean isLiked;

    public void setImages(List<String> urls){
        this.image_url = urls;
    }
}
