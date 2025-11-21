package com.moyeoit.domain.post.repository;

import com.moyeoit.domain.post.model.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);
}
