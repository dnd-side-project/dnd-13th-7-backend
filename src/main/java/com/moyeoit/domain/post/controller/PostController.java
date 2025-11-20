package com.moyeoit.domain.post.controller;

import com.moyeoit.domain.post.controller.request.PostCreateRequest;
import com.moyeoit.domain.post.service.PostService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성 (이미지는 S3 업로드 후 URL 전달)
     *
     **/
    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @Parameter(hidden = true) @CurrentUser AccessUser user,
            @RequestBody PostCreateRequest req
    ) {
        Long createdId = postService.createPost(user.getId(), req);
        return ResponseEntity.ok(ApiResponse.success(createdId));
    }
}
