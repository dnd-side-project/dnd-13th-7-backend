package com.moyeoit.context.community.infra.query;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.moyeoit.context.community.domain.QComment;
import com.moyeoit.context.community.presentation.controller.response.CommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.QCommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.QPostCommentResponse;
import com.moyeoit.context.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommentThreadResponse> getThreads(Long postId, Pageable pageable){

        QComment parent = QComment.comment;
        QComment child = new QComment("child"); //alias

        List<Long> parentIds = queryFactory
                .select(parent.id)
                .from(parent)
                .where(
                        parent.post.id.eq(postId),
                        parent.parent.isNull()
                )
                .orderBy(parent.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (parentIds.isEmpty()) {
            return Page.empty(pageable);
        }

        Long total = getTotal(postId,parent);
        if (total == null || total == 0L) {
            return Page.empty(pageable);
        }

        List<CommentThreadResponse> threads = getAllThreads(parent,parentIds,child);
        return new PageImpl<>(threads, pageable, total);
    }

    private Long getTotal(Long postId,QComment parent) {
        return queryFactory
                .select(parent.count())
                .from(parent)
                .where(
                        parent.post.id.eq(postId),
                        parent.parent.isNull()
                )
                .fetchOne();
    }

    private List<CommentThreadResponse> getAllThreads(QComment parent, List<Long> parentIds, QComment child) {
        QUser parentUser = new QUser("parentUser");
        QUser childUser = new QUser("childUser");

        List<CommentThreadResponse> raw = queryFactory
                .from(parent)
                .leftJoin(parent.user, parentUser)
                .leftJoin(child).on(child.parent.eq(parent))
                .leftJoin(child.user, childUser)
                .where(parent.id.in(parentIds))
                .orderBy(parent.createdAt.asc(), child.createdAt.asc())
                .transform(
                        groupBy(parent.id).list(
                                new QCommentThreadResponse(
                                        new QPostCommentResponse(
                                                parent.id,
                                                parent.user.id,
                                                parentUser.nickname,
                                                parentUser.profileImageUrl,
                                                parent.post.id,
                                                parent.parent.id,
                                                parent.content,
                                                parent.likeCount,
                                                parent.isDeleted,
                                                parent.createdAt,
                                                parent.updatedAt
                                        ),
                                        list(
                                                new QPostCommentResponse(
                                                        child.id,
                                                        child.user.id,
                                                        childUser.nickname,
                                                        childUser.profileImageUrl,
                                                        child.post.id,
                                                        child.parent.id,
                                                        child.content,
                                                        child.likeCount,
                                                        child.isDeleted,
                                                        child.createdAt,
                                                        child.updatedAt
                                                )
                                        )
                                )
                        )
                );

        return raw.stream()
                .map(thread -> new CommentThreadResponse(
                        thread.parent(),
                        thread.children().stream()
                                .filter(c -> c.id() != null)
                                .toList()
                ))
                .toList();
    }

}
