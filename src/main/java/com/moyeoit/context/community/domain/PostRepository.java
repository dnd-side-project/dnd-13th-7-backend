package com.moyeoit.context.community.domain;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);
    Post save(Post post);
    void delete(Post post);
}
