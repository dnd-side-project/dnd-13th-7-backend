package com.moyeoit.context.bookmark.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookmarkType {
    CLUB("동아리"),
    INTERVIEW_REVIEW("서류/면접 후기"),
    ACTIVITY_REVIEW("활동 후기"),
    BLOG_REVIEW("블로그 후기");

    private final String description;
}
