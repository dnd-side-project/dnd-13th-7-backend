package com.moyeoit.domain.review.domain.service;

import com.moyeoit.domain.review.domain.enums.ReviewCategory;
import com.moyeoit.domain.review.domain.enums.ReviewResult;
import com.moyeoit.domain.review.domain.model.Review;
import com.moyeoit.domain.review.infra.ReviewRepository;
import com.moyeoit.domain.review.repository.ReviewLikeRepository;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.infra.jpa.JpaUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// TODO : ReviewConcurrencyTest 개선
// TODO : 삭제 시 동시성 처리 케이스도 추가

@SpringBootTest // 실제 스프링 컨테이너를 띄움 (Real DB 연결)
@ActiveProfiles("local")
class ReviewLikeConcurrencyTest {

    @Autowired
    private ReviewLikeToggleManager reviewLikeToggleManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Autowired
    private JpaUserRepository userRepository;

    private Long reviewId;
    private Long userId;

    @BeforeEach
    void setUp() {
        // 1. 테스트용 유저 생성
        User user = User.builder()
                .name("test_user")
                .email("test")
                .nickname("test")
                .profileImageUrl("")
                .provider(AuthProvider.KAKAO)
                .active(true)
                .jobId(1L)
                .deletedDate(null)
                .deleted(false)
                .build();


        userRepository.save(user);
        userId = user.getId();

        // 2. 테스트용 리뷰 생성 (초기 좋아요 0)
        Review review = Review.builder()
                .title("Test Review")
                .rate(3.5)
                .result(ReviewResult.ACTIVITY)
                .generation(15)
                .category(ReviewCategory.ACTIVITY)
                .jobId(1L)
                .clubId(1L)
                .userId(1L)
                .likeCount(0L)
                .commentCount(0L)
                .build();

        reviewRepository.save(review);
        reviewId = review.getId();
    }

    @AfterEach
    void tearDown() {
        // 테스트가 끝나면 데이터 깔끔하게 삭제
        reviewLikeRepository.deleteAll();
        reviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("동시성 테스트: 한 유저가 동시에 5번 좋아요를 눌러도(따닥), DB에는 1개만 저장되고 카운트는 1만 올라가야 한다.")
    void concurrent_toggle_test() throws InterruptedException {
        // Given
        int threadCount = 5; // 5번 동시 클릭 가정
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount); // 스레드 풀 생성
        CountDownLatch latch = new CountDownLatch(threadCount); // 모든 스레드가 동시에 시작하도록 대기

        // 결과 확인을 위한 성공/실패 카운터 (로그용)
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    // 실제 DB에서 조회해야 하므로 매번 새로 가져옴 (영속성 컨텍스트 분리 효과)
                    // 주의: 멀티스레드 테스트에선 Transaction이 스레드마다 별개로 돔
                    Review review = reviewRepository.findById(reviewId).orElseThrow();
                    User user = userRepository.findById(userId).orElseThrow();

                    reviewLikeToggleManager.toggle(review, user);
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                    System.out.println("에러 발생: " + e.getMessage());
                } finally {
                    latch.countDown(); // 작업 끝날 때마다 카운트 감소
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 메인 스레드 대기

        // Then
        // 1. ReviewLike 테이블에 데이터가 딱 1개만 있어야 함 (중복 저장 방지)
        long actualLikeDataCount = reviewLikeRepository.count();
        Assertions.assertThat(actualLikeDataCount).isEqualTo(1L);

        // 2. Review 테이블의 likeCount가 정확히 1이어야 함
        Review finalReview = reviewRepository.findById(reviewId).orElseThrow();
        System.out.println("최종 좋아요 수: " + finalReview.getLikeCount());

        Assertions.assertThat(finalReview.getLikeCount()).isEqualTo(1);
    }
}