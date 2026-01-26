package com.moyeoit.context.community.presentation.controller;

import com.moyeoit.context.community.infra.query.PostQueryRepository;
import com.moyeoit.context.community.presentation.controller.request.PostCreateRequest;
import com.moyeoit.context.community.presentation.controller.response.PopularPostResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.community.presentation.controller.response.PostDetailInfoResponse;
import com.moyeoit.context.community.presentation.controller.response.PostLikeResponse;
import com.moyeoit.context.community.presentation.swagger.PostApi;
import com.moyeoit.context.community.application.service.PostService;
import com.moyeoit.context.community.domain.CommunityCategoryType;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.auth.argument_resolver.AuthenticateUser;
import com.moyeoit.global.auth.argument_resolver.CurrentUser;
import com.moyeoit.global.response.ApiResponse;
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
public class PostController implements PostApi {

    private final PostService postService;
    private final PostQueryRepository queryRepository;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @CurrentUser AccessUser user,
            @RequestBody PostCreateRequest req
    ) {
        Long createdId = postService.createPost(user.getId(), req);
        return ResponseEntity.ok(ApiResponse.success(createdId));
    }

    @Override
    @GetMapping("/feed")
    public  ResponseEntity<ApiResponse<Page<PostCardResponse>>> feed(
            @PageableDefault(size = 8, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String categoryName
            ) {
        CommunityCategoryType category = CommunityCategoryType.from(categoryName);
        if (categoryName != null && !categoryName.isBlank() && category == null) {
            return ResponseEntity.badRequest().body(ApiResponse.fail("유효하지 않은 카테고리 입니다."));
        }

        Page<PostCardResponse> page = queryRepository.findFeed(category, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    @Override
    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<Page<PopularPostResponse>>> popular(
            @PageableDefault(size = 3, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PopularPostResponse> page = queryRepository.findPopular(pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    @Override
    @GetMapping("/detail/{postId}")
    public ResponseEntity<ApiResponse<PostDetailInfoResponse>> getPostDetailInfo(
            @PathVariable Long postId,
            @CurrentUser AccessUser user) {
        Long viewerId = null;
        if (user instanceof AuthenticateUser authUser) {
            viewerId = authUser.getId();
        }

        PostDetailInfoResponse response = queryRepository.findPostDetailInfo(postId, viewerId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Override
    @PostMapping("/detail/{postId}/like")
    public ResponseEntity<ApiResponse<PostLikeResponse>> like(
            @PathVariable Long postId,
            @CurrentUser AccessUser user) {
        PostLikeResponse response = postService.like(postId,user.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ApiResponse<Page<PostCardResponse>> searchPost(
        @RequestParam String keyword,
        @PageableDefault  Pageable pageable
    ){
        return ApiResponse.success("게시글 검색 성공",queryRepository.searchPostCards(keyword,pageable));
    }
}
