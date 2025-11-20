package com.moyeoit.domain.post.service;

import com.moyeoit.domain.post.controller.request.PostCreateRequest;
import com.moyeoit.domain.post.model.Category;
import com.moyeoit.domain.post.model.Post;
import com.moyeoit.domain.post.model.PostImage;
import com.moyeoit.domain.post.repository.CategoryRepository;
import com.moyeoit.domain.post.repository.PostRepository;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
}
