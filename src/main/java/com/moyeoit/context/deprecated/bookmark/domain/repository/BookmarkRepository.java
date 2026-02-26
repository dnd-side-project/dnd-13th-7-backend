package com.moyeoit.context.deprecated.bookmark.domain.repository;

import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import java.util.Optional;

public interface BookmarkRepository {
    Bookmark save(Bookmark bookmark);
    Optional<Bookmark> findByUserIdAndTargetIdAndType(Long userId, Long targetId, BookmarkType type);
}
