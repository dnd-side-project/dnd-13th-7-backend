package com.moyeoit.domain.post.repository;

import com.moyeoit.domain.post.model.PostLike;
import com.moyeoit.domain.post.model.PostLike.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);

    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);
}
