package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post,Long>{

    @EntityGraph(attributePaths = {"author", "category"})
    @Query("""
            select p from Post p
            where (:status is null or p.status = :status)
              and (:keyword is null or p.title like %:keyword%)
            """)
    Page<Post> searchAdmin(@Param("status") PostStatus status,
                           @Param("keyword") String keyword,
                           Pageable pageable);

    @EntityGraph(attributePaths = {"author", "category"})
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findDetailById(@Param("id") Long id);
}
