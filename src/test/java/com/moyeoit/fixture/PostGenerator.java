package com.moyeoit.fixture;

import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.domain.PostLike.TargetType;
import com.moyeoit.context.user.domain.User;
import java.time.LocalDateTime;

public class PostGenerator {

    public static Category makeCategory(String name) {
        return Category.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Post makePost(User author, Category category) {
        return Post.builder()
                .author(author)
                .category(category)
                .title("테스트 게시글 제목")
                .content("테스트 게시글입니다.")
                .viewCount(123)
                .likeCount(5)
                .commentCount(5)
                .isDeleted(false)
                .build();
    }

    public static Post makePopularPost(User author, Category category) {
        return Post.builder()
                .author(author)
                .category(category)
                .title("인기 게시글 제목")
                .content("인기 게시글입니다.")
                .viewCount(123)
                .likeCount(11)
                .commentCount(5)
                .isDeleted(false)
                .build();
    }

    public static PostLike likePost(User user,Post post){
        return PostLike.builder()
                .userId(user.getId())
                .targetType(TargetType.POST)
                .targetId(post.getId())
                .build();
    }
}
