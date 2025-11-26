package com.moyeoit.domain.post.infra.repository;

import com.moyeoit.domain.post.domain.PostImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
    @Query("""
        select i.imageUrl
        from PostImage i
        where i.post.id = :postId
        order by i.orderIndex asc, i.id asc
        """)
    List<String> findImageUrls(@Param("postId") Long postId);
}