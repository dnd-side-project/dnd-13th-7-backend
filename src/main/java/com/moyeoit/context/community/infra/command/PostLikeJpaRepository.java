package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.domain.PostLike.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);
    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);
}
