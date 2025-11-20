package com.moyeoit.domain.post.repository;

import com.moyeoit.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
