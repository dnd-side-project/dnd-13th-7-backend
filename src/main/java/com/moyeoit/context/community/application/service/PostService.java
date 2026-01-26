package com.moyeoit.context.community.application.service;

import com.moyeoit.context.community.domain.CategoryRepository;
import com.moyeoit.context.community.domain.PostLikeRepository;
import com.moyeoit.context.community.domain.PostRepository;
import com.moyeoit.context.community.presentation.controller.request.PostCreateRequest;
import com.moyeoit.context.community.presentation.controller.response.PostLikeResponse;
import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostImage;
import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.domain.PostLike.TargetType;
import com.moyeoit.context.community.domain.PostType;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostLikeRepository postLikeRepository;

    public Long createPost(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new AppException(UserErrorCode.NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 카테고리 입니다."));
        PostType postType = request.getPostType() != null ? request.getPostType() : PostType.GENERAL;

        Post post = Post.builder()
                .author(user)
                .category(category)
                .title(request.getTitle())
                .content(request.getContent())
                .postType(postType)
                .build();

        request.getImages().forEach(image->{
            boolean isRepresentative = image.getOrderIndex() == 1;
            PostImage postImage = PostImage.builder()
                    .imageUrl(image.getUrl())
                    .orderIndex(image.getOrderIndex())
                    .isRepresentative(isRepresentative)
                    .build();
            post.addImage(postImage);
        });

        Post saved = postRepository.save(post);
        return saved.getId();
    }

    @Transactional
    public PostLikeResponse like(Long postId, Long userId) {

        boolean userExists = userRepository.existsByUserId(userId);
        if (!userExists) {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다. userId=" + userId);
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. postId=" + postId));

        // 이미 좋아요 했는지 확인
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndTargetTypeAndTargetId(userId, TargetType.POST, postId);
        boolean liked;

        if (alreadyLiked) {
            // 좋아요 취소
            postLikeRepository.deleteByUserIdAndTargetTypeAndTargetId(userId, TargetType.POST, postId);
            post.decreaseLikeCount();
            liked = false;
        } else {
            // 좋아요 추가
            PostLike like = PostLike.builder()
                    .userId(userId)
                    .targetType(TargetType.POST)
                    .targetId(postId)
                    .build();

            postLikeRepository.save(like);
            post.increaseLikeCount();
            liked = true;
        }

        return new PostLikeResponse(
                post.getId(),
                liked,
                post.getLikeCount()
        );
    }
}
