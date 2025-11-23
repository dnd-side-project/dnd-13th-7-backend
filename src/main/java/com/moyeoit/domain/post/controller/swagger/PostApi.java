package com.moyeoit.domain.post.controller.swagger;

import com.moyeoit.domain.post.controller.request.PostCreateRequest;
import com.moyeoit.domain.post.controller.response.PopularPostResponse;
import com.moyeoit.domain.post.controller.response.PostCardResponse;
import com.moyeoit.domain.post.controller.response.PostDetailInfoResponse;
import com.moyeoit.domain.post.controller.response.PostLikeResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post", description = "게시글 관련 API")
public interface PostApi {

    @Operation(summary = "게시글 생성 (로그인 필요)", description = "이미지는 S3 업로드 후 URL을 전달합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 생성 성공", content = @Content(schema = @Schema(implementation = Long.class))),
    })
    ResponseEntity<ApiResponse<?>> create(
            @Parameter(hidden = true) AccessUser user,
            @RequestBody PostCreateRequest req
    );

    @Operation(summary = "피드 조회", description = "8개씩 무한스크롤")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "8"),
            @Parameter(name = "sort", description = "정렬, 예: createdAt,desc"),
            @Parameter(name = "categoryId", description = "카테고리 ID")
    })
    ResponseEntity<ApiResponse<Page<PostCardResponse>>> feed(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId
    );

    @Operation(summary = "인기글 조회", description = "좋아요 수, 조회수 순으로 3개씩 조회")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "3"),
            @Parameter(name = "sort", description = "정렬, 예: likeCount,desc"),
            @Parameter(name = "categoryId", description = "카테고리 ID")
    })
    ResponseEntity<ApiResponse<Page<PopularPostResponse>>> popular(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId
    );

    @Operation(summary = "게시글 상세 조회 (로그인 시 좋아요 여부 확인)", description = "게시글의 상세 정보를 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공", content = @Content(schema = @Schema(implementation = PostDetailInfoResponse.class))),
    })
    ResponseEntity<ApiResponse<PostDetailInfoResponse>> getPostDetailInfo(
            @PathVariable Long postId,
            @Parameter(hidden = true) AccessUser user);

    @Operation(summary = "게시글 좋아요 (로그인 필요)", description = "게시글에 좋아요를 누르거나 취소합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 좋아요/취소 성공", content = @Content(schema = @Schema(implementation = PostLikeResponse.class))),
    })
    ResponseEntity<ApiResponse<PostLikeResponse>> like(
            @PathVariable Long postId,
            @Parameter(hidden = true) AccessUser user);
}
