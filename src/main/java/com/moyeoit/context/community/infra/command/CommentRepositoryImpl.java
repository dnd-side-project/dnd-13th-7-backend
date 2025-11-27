package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository jpaRepository;

    @Override
    public Optional<Comment> findByIdAndPostId(Long parentId, Long postId) {
        return jpaRepository.findByIdAndPostId(parentId,postId);
    }

    @Override
    public Page<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId, Pageable pageable) {
        return jpaRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId, pageable);
    }

    @Override
    public List<Comment> findByPostIdAndParentIdInOrderByCreatedAtAsc(Long postId, List<Long> parentIds) {
        return jpaRepository.findByPostIdAndParentIdInOrderByCreatedAtAsc(postId, parentIds);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return jpaRepository.findById(commentId);
    }

    @Override
    public Comment save(Comment saved) {
        return jpaRepository.save(saved);
    }
}
