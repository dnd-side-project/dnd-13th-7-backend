package com.moyeoit.domain.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.Comment;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.infra.query.CommentQueryRepository;
import com.moyeoit.context.community.presentation.controller.response.CommentThreadResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCommentResponse;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.fixture.CommentGenerator;
import com.moyeoit.fixture.JobGenerator;
import com.moyeoit.fixture.PostGenerator;
import com.moyeoit.fixture.UserGenerator;
import com.moyeoit.util.JpaUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({CommentQueryRepositoryTest.TestConfig.class, CommentQueryRepository.class})
public class CommentQueryRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    CommentQueryRepository commentQueryRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager em) {
            return new JPAQueryFactory(em);
        }
    }

    @DisplayName("모든 댓글의 스레드를 조회한다.")
    @Test
    void getThreads_shouldFetchParentAndChildren() {
        // given
        User user = UserGenerator.createActivatedUser(JobGenerator.createJob("학생", "student"));
        Category category = PostGenerator.makeCategory("자유");
        Post post = PostGenerator.makePost(user, category);

        Comment p1 = CommentGenerator.parent(user, post, "parent-1");
        Comment p2 = CommentGenerator.parent(user, post, "parent-2");
        Comment c11 = CommentGenerator.child(user, post, p1, "child-1");
        Comment c12 = CommentGenerator.child(user, post, p1, "child-2");

        JpaUtil.persistAll(em,user,category,post,p1,c11,c12,p2);
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<CommentThreadResponse> result = commentQueryRepository.getThreads(post.getId(), pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);  // 부모 두 개

        CommentThreadResponse t1 = result.getContent().get(0);
        CommentThreadResponse t2 = result.getContent().get(1);

        assertThat(t1.parent().content()).isEqualTo("parent-1");
        assertThat(t1.children())
                .extracting(PostCommentResponse::content)
                .containsExactly("child-1", "child-2");

        assertThat(t2.parent().content()).isEqualTo("parent-2");
        assertThat(t2.children()).isEmpty();
    }
}
