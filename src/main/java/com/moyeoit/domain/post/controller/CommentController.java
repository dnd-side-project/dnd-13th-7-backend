package com.moyeoit.domain.post.controller;

import com.moyeoit.domain.post.controller.request.CommentCreateRequest;
import com.moyeoit.domain.post.controller.request.CommentUpdateRequest;
import com.moyeoit.domain.post.controller.response.CommentThreadResponse;
import com.moyeoit.domain.post.controller.response.PostCommentResponse;
import com.moyeoit.domain.post.service.CommentService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    // 상위댓글 목록 + 각 대댓글 (상위만 페이징)
    @GetMapping("/posts/{postId}/comments")
    public Page<CommentThreadResponse> list(
            @PathVariable Long postId,
            @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.getThreads(postId, pageable);
    }

    //댓글 수정
    @PatchMapping("/comments/{commentId}")
    public PostCommentResponse update(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @RequestBody CommentUpdateRequest request) {
        return commentService.update(commentId, user.getId(), request);
    }

    // 삭제(Soft)
    @DeleteMapping("/comments/{commentId}")
    public void delete(
            @PathVariable Long commentId,
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        commentService.delete(commentId, user.getId());
    }
}
