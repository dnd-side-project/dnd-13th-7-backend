package com.moyeoit.context.bookmark.presentation.response;

import com.moyeoit.context.bookmark.presentation.request.BookmarkType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "북마크 응답")
public class BookmarkResponse {

    @Schema(description = "북마크 여부 (true: 북마크 됨, false: 북마크 취소됨)", example = "true")
    private Boolean isBookmarked;

    @Schema(description = "북마크 타입", example = "CLUB")
    private BookmarkType type;

    @Schema(description = "북마크 대상 ID", example = "1")
    private Long targetId;

}
