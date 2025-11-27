package com.moyeoit.context.community.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);

    Page<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId, Pageable pageable);

    List<Comment> findByPostIdAndParentIdInOrderByCreatedAtAsc(Long postId, List<Long> parentIds);

    Optional<Comment> findById(Long commentId);

    Comment save(Comment saved);
}
