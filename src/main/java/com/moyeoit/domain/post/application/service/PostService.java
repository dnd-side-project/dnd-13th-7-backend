package com.moyeoit.domain.post.application.service;

import com.moyeoit.domain.post.presentation.request.PostCreateRequest;
import com.moyeoit.domain.post.presentation.response.PopularPostResponse;
import com.moyeoit.domain.post.presentation.response.PostCardResponse;
import com.moyeoit.domain.post.presentation.response.PostDetailInfoResponse;
import com.moyeoit.domain.post.presentation.response.PostLikeResponse;
import com.moyeoit.domain.post.domain.Category;
import com.moyeoit.domain.post.domain.Post;
import com.moyeoit.domain.post.domain.PostImage;
import com.moyeoit.domain.post.domain.PostLike;
import com.moyeoit.domain.post.domain.PostLike.TargetType;
import com.moyeoit.domain.post.infra.repository.CategoryRepository;
import com.moyeoit.domain.post.infra.repository.PostImageRepository;
import com.moyeoit.domain.post.infra.repository.PostLikeRepository;
import com.moyeoit.domain.post.infra.repository.PostRepository;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;

    public Long createPost(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new AppException(UserErrorCode.NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()->new IllegalArgumentException("없는 카테고리 입니다."));

        Post post = Post.builder()
                .author(user)
                .category(category)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        request.getImages().forEach(image->{
            PostImage postImage = PostImage.builder()
                    .imageUrl(image.getUrl())
                    .orderIndex(image.getOrderIndex())
                    .build();
            post.addImage(postImage);
        });

        Post saved = postRepository.save(post);
        return saved.getId();
    }

    public Page<PostCardResponse> getFeed(Long categoryId, Pageable pageable) {
        return postRepository.findFeed(categoryId,pageable);
    }

    public Page<PopularPostResponse> getPopular(Long categoryId,Pageable pageable) {
        return postRepository.findPopular(categoryId,pageable);
    }

    public PostDetailInfoResponse getDetailInfo(Long postId, Long userId) {
        PostDetailInfoResponse response = postRepository.findPostDetailInfo(postId, userId).orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다.: " + postId));
        List<String> imageUrls = postImageRepository.findImageUrls(postId);

        response.setImages(imageUrls);
        return response;
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

    public Page<PostCardResponse> search(String keyword,Pageable pageable) {
        return postRepository.searchPostCards(keyword,pageable);
    }
}
