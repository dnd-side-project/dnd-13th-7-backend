package com.moyeoit.context.bookmark.infra.query;

import static com.moyeoit.context.bookmark.infra.entity.QBookmarkEntity.bookmarkEntity;
import static com.moyeoit.context.club.domain.entity.QClub.club;
import static com.moyeoit.context.club.domain.entity.position.QClubPosition.clubPosition;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.moyeoit.context.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.club.presentation.response.ClubListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ClubListResponse> findBookmarkedClubs(Long userId, Pageable pageable) {
        List<Long> clubIds = queryFactory
                .select(club.id)
                .from(bookmarkEntity)
                .join(club).on(bookmarkEntity.targetId.eq(club.id))
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.CLUB),
                        bookmarkEntity.isActive.isTrue()
                )
                .orderBy(bookmarkEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (clubIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        List<ClubListResponse> pagedContent = queryFactory
                .selectFrom(club)
                .leftJoin(club.positions, clubPosition)
                .where(club.id.in(clubIds))
                .transform(
                        groupBy(club.id).list(
                                Projections.constructor(ClubListResponse.class,
                                        club.id,
                                        club.name,
                                        club.clubProfile.bio,
                                        list(clubPosition.name),
                                        club.clubProfile.imageUrl,
                                        club.recruiting
                                )
                        )
                );

        List<ClubListResponse> sortedContent = clubIds.stream()
                .map(id -> pagedContent.stream()
                        .filter(c -> c.clubId().equals(id))
                        .findFirst()
                        .orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();

        Long total = queryFactory
                .select(bookmarkEntity.count())
                .from(bookmarkEntity)
                .where(
                        bookmarkEntity.userId.eq(userId),
                        bookmarkEntity.type.eq(BookmarkType.CLUB),
                        bookmarkEntity.isActive.isTrue()
                )
                .fetchOne();

        return new PageImpl<>(sortedContent, pageable, total != null ? total : 0);
    }
}
