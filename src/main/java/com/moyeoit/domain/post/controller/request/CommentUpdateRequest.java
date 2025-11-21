package com.moyeoit.domain.post.controller.request;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {
    private Long parentId;
    private String content;
}
