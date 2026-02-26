package com.moyeoit.context.deprecated.bookmark.service;

import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.domain.repository.BookmarkRepository;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkCreateRequest;
import com.moyeoit.context.deprecated.bookmark.presentation.response.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkResponse toggleBookmark(Long userId, BookmarkCreateRequest request) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndTargetIdAndType(userId, request.getTargetId(), request.getType())
                .orElse(null);

        if (bookmark == null) {
            bookmark = Bookmark.create(userId, request.getTargetId(), request.getType());
            bookmarkRepository.save(bookmark);
        } else {
            bookmark.toggle();
            bookmarkRepository.save(bookmark);
        }

        return new BookmarkResponse(bookmark.getIsActive(), bookmark.getType(), bookmark.getTargetId());
    }
}
