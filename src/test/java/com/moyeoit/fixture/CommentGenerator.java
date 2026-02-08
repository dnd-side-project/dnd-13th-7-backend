package com.moyeoit.fixture;

import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.user.domain.User;

public class CommentGenerator {

    public static Comment parent(User user, Post post, String content) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }

    public static Comment child(User user, Post post, Comment parent, String content) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parent(parent)
                .content(content)
                .build();
    }
}
