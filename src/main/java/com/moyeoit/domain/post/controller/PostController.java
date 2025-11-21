package com.moyeoit.domain.post.controller;

import com.moyeoit.domain.post.controller.request.PostCreateRequest;
import com.moyeoit.domain.post.controller.response.PopularPostResponse;
import com.moyeoit.domain.post.controller.response.PostCardResponse;
import com.moyeoit.domain.post.controller.response.PostDetailInfoResponse;
import com.moyeoit.domain.post.controller.response.PostLikeResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    public  ResponseEntity<ApiResponse<Page<PostCardResponse>>> feed(
            @PageableDefault(size = 8, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Long categoryId
            ) {
        Page<PostCardResponse> page = postService.getFeed(categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    //인기글: likeCount desc, viewCount desc 3개씩
    // 예: GET /api/v2/post/popular?page=0&size=3
    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<Page<PopularPostResponse>>> popular(
            @PageableDefault(size = 3, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Long categoryId
    ) {
        Page<PopularPostResponse> page = postService.getPopular(categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    /**
     * 게시글 상세조회
     *
     **/
    @GetMapping("/detail/{postId}")
    public ResponseEntity<ApiResponse<PostDetailInfoResponse>> getPostDetailInfo(
            @PathVariable Long postId,
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        PostDetailInfoResponse response = postService.getDetailInfo(postId, user.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 좋아요를 누르거나 좋아요를 취소하는 기능
    @PostMapping("/detail/{postId}/like")
    public ResponseEntity<ApiResponse<PostLikeResponse>> like(
            @PathVariable Long postId,
            @Parameter(hidden = true) @CurrentUser AccessUser user) {
        PostLikeResponse response = postService.like(postId,user.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
