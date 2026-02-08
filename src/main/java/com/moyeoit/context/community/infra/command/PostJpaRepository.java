package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post,Long>{
}
