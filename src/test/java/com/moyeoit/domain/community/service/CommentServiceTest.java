package com.moyeoit.domain.community.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.context.community.application.service.CommentService;
import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostRepository;
import com.moyeoit.context.community.infra.command.CategoryJpaRepository;
import com.moyeoit.context.community.presentation.controller.request.CommentCreateRequest;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.fixture.JobGenerator;
import com.moyeoit.fixture.PostGenerator;
import com.moyeoit.fixture.UserGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired UserRepository userRepository;
    @Autowired CategoryJpaRepository jpaRepository;
    @Autowired PostRepository postRepository;
    @Autowired EntityManager em;

    @Test
    void increase_commentCount() {
        // given: 반드시 save 해서 ID를 만든다
        Long userId = userRepository.save(
                UserGenerator.createActivatedUser(JobGenerator.createJob("학생", "student"))
        );
        User findUser = userRepository.findById(userId).get();
        Category category = jpaRepository.save(
                PostGenerator.makeCategory("자유")
        );
        Post post = postRepository.save(
                PostGenerator.makePost(findUser, category)
        );

        // when
        commentService.create(post.getId(), findUser.getId(), new CommentCreateRequest("저도 치킨 좋아해요!", null));

        em.flush();
        em.clear();

        // then: flush/clear 했으면 '다시 조회'한 값을 검증해야 한다
        Post reloaded = postRepository.findById(post.getId()).orElseThrow();
        assertThat(reloaded.getCommentCount()).isEqualTo(6);
    }
}
