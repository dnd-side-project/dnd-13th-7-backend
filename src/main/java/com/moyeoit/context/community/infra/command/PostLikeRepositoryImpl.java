package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.domain.PostLike.TargetType;
import com.moyeoit.context.community.domain.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {

    private final PostLikeJpaRepository jpaRepository;

    @Override
    public void save(PostLike like) {
        jpaRepository.save(like);
    }

    @Override
    public void deleteByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId) {
        jpaRepository.deleteByUserIdAndTargetTypeAndTargetId(userId,targetType,postId);
    }

    @Override
    public boolean existsByUserIdAndTargetTypeAndTargetId(Long userId, TargetType targetType, Long postId) {
        return jpaRepository.existsByUserIdAndTargetTypeAndTargetId(userId, targetType, postId);
    }
}
