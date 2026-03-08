package com.moyeoit.domain.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.CommunityCategoryType;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.domain.PostType;
import com.moyeoit.context.community.infra.query.PostQueryRepository;
import com.moyeoit.context.community.presentation.controller.response.PopularPostResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.community.presentation.controller.response.PostDetailInfoResponse;
import com.moyeoit.context.user.domain.Job;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.fixture.JobGenerator;
import com.moyeoit.fixture.PostGenerator;
import com.moyeoit.util.JpaUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
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
@Import({PostQueryRepositoryTest.TestConfig.class, PostQueryRepository.class})
public class PostQueryRepositoryTest {

    @Autowired
    PostQueryRepository postQueryRepository;

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager em) {
            return new JPAQueryFactory(em);
        }
    }

    Post post1;
    Post post2;
    private User author;
    private Job authorJob;
    private Category categoryFree;

    @BeforeEach
    void setUp() {
        authorJob = JobGenerator.createJob("학생", "student");
        categoryFree = PostGenerator.makeCategory("자유");
        em.persist(authorJob);
        author = User.builder()
                .name("홍길동")
                .email("test@test.com")
                .nickname("홍길동 닉네임")
                .profileImageUrl("")
                .provider(com.moyeoit.context.user.domain.AuthProvider.GOOGLE)
                .active(true)
                .jobId(authorJob.getId())
                .deleted(false)
                .build();
        post1 = PostGenerator.makePost(author, categoryFree);
        post2 = PostGenerator.makePost(author, categoryFree);
        JpaUtil.persistAll(em, author, categoryFree, post1, post2);
        em.flush();
        em.clear();
    }

    @DisplayName("여러 게시물의 목록을 조회한다.")
    @Test
    void get_a_list_of_multiple_posts() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<PostCardResponse> result = postQueryRepository.findFeed(CommunityCategoryType.FREE, pageable);
        PostCardResponse first = result.getContent().getFirst();

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(first.categoryName()).isEqualTo("자유");
        assertThat(first.title()).isEqualTo("테스트 게시글 제목");
        assertThat(first.excerpt()).contains("테스트 게시글입니다.");
        assertThat(first.authorNickname()).isEqualTo("홍길동 닉네임");
        assertThat(first.authorJobName()).isEqualTo("학생");
    }

    @DisplayName("질문 카테고리 피드는 질문글만 모아 조회한다.")
    @Test
    void get_question_posts_in_question_category_feed() {
        Post questionPost = Post.builder()
                .author(author)
                .category(categoryFree)
                .title("질문 게시글 제목")
                .content("질문 게시글입니다.")
                .postType(PostType.QUESTION)
                .viewCount(10)
                .likeCount(1)
                .commentCount(1)
                .isDeleted(false)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        em.persist(questionPost);
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 10);

        Page<PostCardResponse> result = postQueryRepository.findFeed(CommunityCategoryType.QUESTION, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().title()).isEqualTo("질문 게시글 제목");
        assertThat(result.getContent().getFirst().postType()).isEqualTo(PostType.QUESTION);
    }

    @DisplayName("인기 게시물을 조회한다")
    @Test
    void get_a_find_popular_post() {
        Post popular = PostGenerator.makePopularPost(author,categoryFree);
        em.persist(popular);
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 3);
        Page<PopularPostResponse> result = postQueryRepository.findPopular(pageable);

        PopularPostResponse response = result.getContent().getFirst();

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(response.title()).isEqualTo("인기 게시글 제목");
        assertThat(response.excerpt()).contains("인기 게시글입니다.");
    }

    @DisplayName("게시물을 상세조회하고 좋아요 여부를 확인한다.")
    @Test
    void check_whether_the_post_has_been_liked(){
        PostLike like = PostGenerator.likePost(author,post1);
        em.persist(like);
        em.flush();
        em.clear();

        PostDetailInfoResponse result = postQueryRepository.findPostDetailInfo(post1.getId(),author.getId());

        assertThat(result.getView_count()).isEqualTo(123);
        assertThat(result.getLike_count()).isEqualTo(5);
        assertThat(result.isLiked()).isTrue();
        assertThat(result.isHotPost()).isTrue();
        assertThat(result.getAuthorProfileImageUrl()).isEqualTo(author.getProfileImageUrl());
    }

    @DisplayName("키워드를 제목에 포함하는 게시물을 검색한다.")
    @Test
    void search_for_posts_that_include_keyword_in_the_title(){
        Post post3 = PostGenerator.makePost(author,categoryFree);
        em.persist(post3);
        em.flush();
        em.clear();
        Pageable pageable = PageRequest.of(0, 3);

        Page<PostCardResponse> result = postQueryRepository.searchPostCards("테스트",pageable);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }
}
