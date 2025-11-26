package com.moyeoit.domain.post.infra.repository;

import com.moyeoit.domain.post.domain.PostLike;
import com.moyeoit.domain.post.domain.PostLike.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);

    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);
}
