package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment,Long>{
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);
}
