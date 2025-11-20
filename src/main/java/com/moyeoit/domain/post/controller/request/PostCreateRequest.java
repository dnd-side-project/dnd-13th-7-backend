package com.moyeoit.domain.post.controller.request;

import java.util.List;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    private Long categoryId;
    private String title;
    private String content;
    List<PostCreateImage> images;

    @Getter
    public static class PostCreateImage{
        private String url;
        private Integer orderIndex;
    }
}
