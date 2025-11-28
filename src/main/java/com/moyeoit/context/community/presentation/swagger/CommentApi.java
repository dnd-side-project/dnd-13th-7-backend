package com.moyeoit.context.community.presentation.swagger;

import com.moyeoit.context.community.presentation.controller.request.CommentCreateRequest;
import com.moyeoit.context.community.presentation.controller.request.CommentUpdateRequest;
import com.moyeoit.context.community.presentation.controller.response.CommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCommentResponse;
import com.moyeoit.global.auth.argument_resolver.AccessUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "커뮤니티 댓글 API", description = "커뮤니티의 댓글을 관리하는 API입니다.")
public interface CommentApi {

    @Operation(summary = "댓글 작성 (로그인 필요)", description = "상위 댓글 또는 대댓글을 작성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = PostCommentResponse.class)))
    })
    PostCommentResponse create(
            @PathVariable Long postId,
            @Parameter(hidden = true) AccessUser user,
            @RequestBody CommentCreateRequest request);

    @Operation(summary = "댓글 목록 조회", description = "상위 댓글 목록과 각 대댓글을 조회합니다. (상위 댓글만 페이징)")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "5"),
            @Parameter(name = "sort", description = "정렬, 예: createdAt,desc")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", content = @Content(schema = @Schema(implementation = CommentThreadResponse.class)))
    })
    Page<CommentThreadResponse> list(
            @PathVariable Long postId,
            Pageable pageable);

    @Operation(summary = "댓글 수정 (로그인 필요)", description = "댓글 내용을 수정합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = PostCommentResponse.class)))
    })
    PostCommentResponse update(
            @PathVariable Long commentId,
            @Parameter(hidden = true) AccessUser user,
            @RequestBody CommentUpdateRequest request);

    @Operation(summary = "댓글 삭제 (로그인 필요)", description = "댓글을 삭제합니다. (Soft delete)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
    })
    void delete(
            @PathVariable Long commentId,
            @Parameter(hidden = true) AccessUser user);
}
