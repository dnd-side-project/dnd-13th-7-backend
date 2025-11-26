package com.moyeoit.domain.post.infra.repository;

import com.moyeoit.domain.post.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);

    Page<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId, Pageable pageable);

    List<Comment> findByPostIdAndParentIdInOrderByCreatedAtAsc(Long postId, List<Long> parentIds);
}
