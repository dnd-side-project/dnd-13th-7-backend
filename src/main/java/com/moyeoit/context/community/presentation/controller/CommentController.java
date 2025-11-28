package com.moyeoit.context.community.presentation.controller;

import com.moyeoit.context.community.infra.query.CommentQueryRepository;
import com.moyeoit.context.community.presentation.controller.request.CommentCreateRequest;
import com.moyeoit.context.community.presentation.controller.request.CommentUpdateRequest;
import com.moyeoit.context.community.presentation.controller.response.CommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCommentResponse;
import com.moyeoit.context.community.presentation.swagger.CommentApi;
import com.moyeoit.context.community.application.service.CommentService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
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
public class CommentController implements CommentApi {

    private final CommentService commentService;
    private final CommentQueryRepository queryRepository;

    @Override
    @PostMapping("/posts/{postId}/comments")
    public PostCommentResponse create(
            @PathVariable Long postId,
            @CurrentUser AccessUser user,
            @RequestBody CommentCreateRequest request) {
        return commentService.create(postId, user.getId(), request);
    }

    @Override
    @GetMapping("/posts/{postId}/comments")
    public Page<CommentThreadResponse> list(
            @PathVariable Long postId,
            @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        return queryRepository.getThreads(postId, pageable);
    }

    @Override
    @PatchMapping("/comments/{commentId}")
    public PostCommentResponse update(
            @PathVariable Long commentId,
            @CurrentUser AccessUser user,
            @RequestBody CommentUpdateRequest request) {
        return commentService.update(commentId, user.getId(), request);
    }

    @Override
    @DeleteMapping("/comments/{commentId}")
    public void delete(
            @PathVariable Long commentId,
            @CurrentUser AccessUser user) {
        commentService.delete(commentId, user.getId());
    }
}
