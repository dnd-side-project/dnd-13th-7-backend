package com.moyeoit.context.deprecated.bookmark.domain.model;

import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Bookmark {

    private Long id;
    private Long userId;
    private Long targetId;
    private BookmarkType type;
    private Boolean isActive;

    public void toggle() {
        this.isActive = !this.isActive;
    }

    public static Bookmark create(Long userId, Long targetId, BookmarkType type) {
        return Bookmark.builder()
                .userId(userId)
                .targetId(targetId)
                .type(type)
                .isActive(true)
                .build();
    }
}
