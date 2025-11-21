package com.moyeoit.domain.post.controller;

import com.moyeoit.domain.post.controller.request.CommentCreateRequest;
import com.moyeoit.domain.post.controller.response.PostCommentResponse;
import com.moyeoit.domain.post.service.CommentService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v2")
public class CommentController {
    private final CommentService commentService;

    // 상위댓글/대댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public PostCommentResponse create(
            @PathVariable Long postId,
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @RequestBody CommentCreateRequest request) {
        return commentService.create(postId, user.getId(), request);
    }
}
