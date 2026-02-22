package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.CommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentJpaRepository extends JpaRepository<Comment,Long>{
    Optional<Comment> findByIdAndPostId(Long parentId, Long postId);

    @EntityGraph(attributePaths = {"user", "post"})
    @Query("""
            select c from Comment c
            where (:status is null or c.status = :status)
              and (:keyword is null or c.content like %:keyword%)
            """)
    Page<Comment> searchAdmin(@Param("status") CommentStatus status,
                              @Param("keyword") String keyword,
                              Pageable pageable);

    @EntityGraph(attributePaths = {"user", "post"})
    @Query("select c from Comment c where c.id = :id")
    Optional<Comment> findDetailById(@Param("id") Long id);
}
