package com.moyeoit.domain.post.service;

import com.moyeoit.domain.post.controller.request.CommentCreateRequest;
import com.moyeoit.domain.post.controller.response.PostCommentResponse;
import com.moyeoit.domain.post.model.Comment;
import com.moyeoit.domain.post.model.Post;
import com.moyeoit.domain.post.repository.CommentRepository;
import com.moyeoit.domain.post.repository.PostRepository;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCommentResponse create(Long postId, Long userId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(UserErrorCode.NOT_FOUND));
        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findByIdAndPostId(request.getParentId(), postId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 parentId"));

            if (parent.getParent() != null) {
                throw new IllegalArgumentException("depth는 1까지만 허용됩니다.");
            }
        }

        Comment saved = Comment.builder()
                .post(post)
                .parent(parent)
                .user(user)
                .content(request.getContent())
                .build();
        saved = commentRepository.save(saved);

        return PostCommentResponse.from(saved);
    }
}