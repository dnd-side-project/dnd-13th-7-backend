package com.moyeoit.context.deprecated.bookmark.infra.command;

import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.domain.repository.BookmarkRepository;
import com.moyeoit.context.deprecated.bookmark.infra.entity.BookmarkEntity;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepository {

    private final BookmarkJpaRepository bookmarkJpaRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkJpaRepository.save(BookmarkEntity.from(bookmark)).toModel();
    }

    @Override
    public Optional<Bookmark> findByUserIdAndTargetIdAndType(Long userId, Long targetId, BookmarkType type) {
        return bookmarkJpaRepository.findByUserIdAndTargetIdAndType(userId, targetId, type)
                .map(BookmarkEntity::toModel);
    }
}
