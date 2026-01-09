package com.moyeoit.context.bookmark.service;

import com.moyeoit.context.bookmark.domain.model.Bookmark;
import com.moyeoit.context.bookmark.domain.repository.BookmarkRepository;
import com.moyeoit.context.bookmark.infra.query.BookmarkQueryRepository;
import com.moyeoit.context.bookmark.presentation.request.BookmarkCreateRequest;
import com.moyeoit.context.bookmark.presentation.response.BookmarkResponse;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkQueryRepository bookmarkQueryRepository;

    @Transactional(readOnly = true)
    public Page<ClubListResponse> getBookmarkedClubs(Long userId, Pageable pageable) {
        return bookmarkQueryRepository.findBookmarkedClubs(userId, pageable);
    }

    @Transactional
    public BookmarkResponse toggleBookmark(Long userId, BookmarkCreateRequest request) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndTargetIdAndType(userId, request.getTargetId(), request.getType())
                .orElse(null);

        if (bookmark == null) {
            bookmark = Bookmark.create(userId, request.getTargetId(), request.getType());
            bookmarkRepository.save(bookmark);
        } else {
            bookmark.toggle();
        }

        return new BookmarkResponse(bookmark.getIsActive(), bookmark.getType(), bookmark.getTargetId());
    }
}
