package com.moyeoit.domain.post.controller;

import com.moyeoit.domain.post.controller.request.PostCreateRequest;
import com.moyeoit.domain.post.controller.response.PopularPostResponse;
import com.moyeoit.domain.post.controller.response.PostCardResponse;
import com.moyeoit.domain.post.service.PostService;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/post")
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

    // 일반 피드: 8개씩 무한스크롤
    // 예: GET /api/v2/post/feed?page=0&size=8&categoryId=&
    @GetMapping("/feed")
    public Page<PostCardResponse> feed(
            @PageableDefault(size = 8, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Long categoryId
            ) {
        return postService.getFeed(categoryId, pageable);
    }

    //인기글: likeCount desc, viewCount desc 3개씩
    // 예: GET /api/v2/post/popular?page=0&size=3
    @GetMapping("/popular")
    public Page<PopularPostResponse> popular(
            @PageableDefault(size = 3, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Long categoryId
    ) {
        return postService.getPopular(categoryId, pageable);
    }
}
