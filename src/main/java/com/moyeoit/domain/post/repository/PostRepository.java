package com.moyeoit.domain.post.repository;

import com.moyeoit.domain.post.controller.response.PopularPostResponse;
import com.moyeoit.domain.post.controller.response.PostCardResponse;
import com.moyeoit.domain.post.controller.response.PostDetailInfoResponse;
import com.moyeoit.domain.post.model.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = """
            select new com.moyeoit.domain.post.controller.response.PostCardResponse(
                        p.id,
                        p.title,
                        substring(p.content, 1, 100),
                        (select pi.imageUrl from PostImage pi where pi.post = p and pi.isRepresentative = true),
                        p.category.id,
                        p.category.name,
                        p.author.nickname,
                        p.viewCount,
                        p.likeCount,
                        p.commentCount,
                        p.createdAt
                        )
            from Post p
            where p.isDeleted = false
            and (:categoryId is null or p.category.id = :categoryId)
            and (COALESCE(:categoryId, 0) <> 1 or p.likeCount >= 10)
            order by p.createdAt desc
            """,
            countQuery = """
        select count(p)
        from Post p
            where p.isDeleted = false
            and (:categoryId is null or p.category.id = :categoryId)
            and (COALESCE(:categoryId, 0) <> 1 or p.likeCount >= 10)
        """
    )
    Page<PostCardResponse> findFeed(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = """
            select new com.moyeoit.domain.post.controller.response.PopularPostResponse(
                p.id,
                p.title,
                substring(p.content, 1, 100),
                p.category.id,
                p.category.name,
                p.likeCount,
                p.commentCount
                )
            from Post p
            where p.isDeleted = false
            order by p.createdAt desc, p.likeCount desc
    """)
    Page<PopularPostResponse> findPopular(Long categoryId,Pageable pageable);

    @Query("""
    select new com.moyeoit.domain.post.controller.response.PostDetailInfoResponse(
        case when p.likeCount >= 10 then true else false end,
        p.category.name,
        p.title,
        p.author.nickname,
        p.content,
        null,
        p.createdAt,
        p.viewCount,
        p.likeCount,
        p.commentCount,
        case
            when :viewerId is null then false
            when exists (
                select 1 from PostLike pl
                where pl.targetType = com.moyeoit.domain.post.model.PostLike.TargetType.POST
                  and pl.targetId = p.id
                  and pl.userId = :viewerId
            ) then true else false
        end
    )
    from Post p
    where p.id = :postId
    """)
    Optional<PostDetailInfoResponse> findPostDetailInfo(
            @Param("postId") Long postId,
            @Param("viewerId") Long viewerId);
}
