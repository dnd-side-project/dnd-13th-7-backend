package com.moyeoit.domain.post.controller.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private String content;
    private Long parentId;
}
