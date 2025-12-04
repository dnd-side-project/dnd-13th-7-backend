package com.moyeoit.context.community.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);

    Optional<Comment> findById(Long commentId);

    Comment save(Comment saved);
}
