package com.moyeoit.context.admin.application;

import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.CommentRepository;
import com.moyeoit.context.community.domain.CommentStatus;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostRepository;
import com.moyeoit.context.community.domain.PostStatus;
import com.moyeoit.context.community.infra.command.CommentJpaRepository;
import com.moyeoit.context.community.infra.command.PostJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminCommunityService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostJpaRepository postJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    @Transactional(readOnly = true)
    public Page<Post> getPosts(String keyword, PostStatus status, Pageable pageable) {
        String searchKeyword = StringUtils.hasText(keyword) ? keyword : null;
        return postJpaRepository.searchAdmin(status, searchKeyword, pageable);
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postJpaRepository.findDetailById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found. id=" + postId));
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = getPost(postId);
        post.markDeleted();
    }

    @Transactional
    public void restorePost(Long postId) {
        Post post = getPost(postId);
        post.restore();
    }

    @Transactional
    public void blindPost(Long postId, String memo) {
        Post post = getPost(postId);
        post.blind(memo);
    }

    @Transactional
    public void unblindPost(Long postId) {
        Post post = getPost(postId);
        post.unblind();
    }

    @Transactional(readOnly = true)
    public Page<Comment> getComments(String keyword, CommentStatus status, Pageable pageable) {
        String searchKeyword = StringUtils.hasText(keyword) ? keyword : null;
        return commentJpaRepository.searchAdmin(status, searchKeyword, pageable);
    }

    @Transactional(readOnly = true)
    public Comment getComment(Long commentId) {
        return commentJpaRepository.findDetailById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found. id=" + commentId));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getComment(commentId);
        comment.commentDelete();
    }

    @Transactional
    public void restoreComment(Long commentId) {
        Comment comment = getComment(commentId);
        comment.restore();
    }

    @Transactional
    public void blindComment(Long commentId, String memo) {
        Comment comment = getComment(commentId);
        comment.blind(memo);
    }

    @Transactional
    public void unblindComment(Long commentId) {
        Comment comment = getComment(commentId);
        comment.unblind();
    }

 
}
