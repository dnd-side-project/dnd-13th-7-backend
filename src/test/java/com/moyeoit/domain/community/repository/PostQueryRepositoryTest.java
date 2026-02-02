package com.moyeoit.domain.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.context.community.domain.Category;
import com.moyeoit.context.community.domain.CommunityCategoryType;
import com.moyeoit.context.community.domain.Post;
import com.moyeoit.context.community.domain.PostLike;
import com.moyeoit.context.community.infra.query.PostQueryRepository;
import com.moyeoit.context.community.presentation.controller.response.PopularPostResponse;
import com.moyeoit.context.community.presentation.controller.response.PostCardResponse;
import com.moyeoit.context.community.presentation.controller.response.PostDetailInfoResponse;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.fixture.JobGenerator;
import com.moyeoit.fixture.PostGenerator;
import com.moyeoit.fixture.UserGenerator;
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
    private Category categoryFree;

    @BeforeEach
    void setUp() {
        author = UserGenerator.createActivatedUser(JobGenerator.createJob("학생", "student"));
        categoryFree = PostGenerator.makeCategory("자유");
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

        assertThat(result.getTotalElements()).isEqualTo(1);
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
