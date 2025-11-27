package com.moyeoit.context.community.domain;

import com.moyeoit.context.community.domain.PostLike.TargetType;

public interface PostLikeRepository {
    void save(PostLike like);

    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);

    boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId);
}
