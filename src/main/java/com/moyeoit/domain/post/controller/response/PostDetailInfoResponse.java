package com.moyeoit.domain.post.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDetailInfoResponse {
    boolean isHotPost;
    String categoryName;
    String title;
    String nickname;
    String content;
    List<String> image_url;
    LocalDateTime create_at;
    int view_count;
    int like_count;
    int comment_count;
    boolean isLiked;

    public void setImages(List<String> urls){
        this.image_url = urls;
    }
}
