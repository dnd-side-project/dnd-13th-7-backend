package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository jpaRepository;

    @Override
    public Optional<Post> findById(Long postId) {
        return jpaRepository.findById(postId);
    }

    @Override
    public Post save(Post post) {
        return jpaRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        jpaRepository.delete(post);
    }
}
