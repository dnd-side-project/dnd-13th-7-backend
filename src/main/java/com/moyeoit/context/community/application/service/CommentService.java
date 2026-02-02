package com.moyeoit.context.community.application.service;

import com.moyeoit.context.community.domain.CommentRepository;
import com.moyeoit.context.community.domain.PostRepository;
import com.moyeoit.context.community.presentation.controller.request.CommentCreateRequest;
import com.moyeoit.context.community.presentation.controller.request.CommentUpdateRequest;
import com.moyeoit.context.community.presentation.controller.response.CommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCommentResponse;
import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        post.increaseCommentCount();
        return PostCommentResponse.from(saved);
    }

    @Transactional
    public PostCommentResponse update(Long commentId, Long userId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        if(!comment.getUser().getId().equals(userId)){
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }

        if (comment.getIsDeleted()){
            throw new IllegalStateException("삭제된 댓글은 수정할 수 없습니다.");
        }

        comment.updateContent(request.getContent());
        return PostCommentResponse.from(comment);
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        if(!comment.getUser().getId().equals(userId)){
            throw new SecurityException("작성자만 삭제할 수 있습니다.");
        }
        Post currentPost = comment.getPost();
        currentPost.decreaseCommentCount();
        comment.commentDelete();
    }
}
