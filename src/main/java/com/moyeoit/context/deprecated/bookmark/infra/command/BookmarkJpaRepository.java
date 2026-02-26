package com.moyeoit.context.deprecated.bookmark.infra.command;

import com.moyeoit.context.deprecated.bookmark.infra.entity.BookmarkEntity;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkJpaRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByUserIdAndTargetIdAndType(Long userId, Long targetId, BookmarkType type);
}
