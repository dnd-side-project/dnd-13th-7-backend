package com.moyeoit.domain.bookmark;
import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.domain.repository.BookmarkRepository;
import com.moyeoit.context.deprecated.bookmark.infra.query.BookmarkQueryRepository;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.repository.ClubRepository;
import com.moyeoit.context.deprecated.review.domain.model.Review;
import com.moyeoit.context.deprecated.review.infra.ReviewRepository;
import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.infra.jpa.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private BookmarkQueryRepository bookmarkQueryRepository;
    @Autowired
    private JpaUserRepository userRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private User user;
    private Club club1, club2;
    private Review interviewReview, activityReview, blogReview;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name("testUser")
                .email("test@test.com")
                .provider(AuthProvider.KAKAO)
                .active(true)
                .build());
        club1 = clubRepository.save(Club.builder().name("Club 1").build());
        club2 = clubRepository.save(Club.builder().name("Club 2").build());
        interviewReview = reviewRepository.save(Review.builder().title("Interview Review").category(com.moyeoit.context.deprecated.review.domain.enums.ReviewCategory.INTERVIEW).build());
        activityReview = reviewRepository.save(Review.builder().title("Activity Review").category(com.moyeoit.context.deprecated.review.domain.enums.ReviewCategory.ACTIVITY).build());
        blogReview = reviewRepository.save(Review.builder().title("Blog Review").category(com.moyeoit.context.deprecated.review.domain.enums.ReviewCategory.BLOG).build());
    }

    @Test
    @DisplayName("북마크를 저장하고 조회할 수 있다.")
    void saveAndFindBookmark() {
        // Given
        Bookmark bookmark = Bookmark.create(user.getId(), club1.getId(), BookmarkType.CLUB);

        // When
        bookmarkRepository.save(bookmark);
        Bookmark foundBookmark = bookmarkRepository.findByUserIdAndTargetIdAndType(user.getId(), club1.getId(), BookmarkType.CLUB).orElse(null);

        // Then
        assertThat(foundBookmark).isNotNull();
        assertThat(foundBookmark.getUserId()).isEqualTo(user.getId());
        assertThat(foundBookmark.getTargetId()).isEqualTo(club1.getId());
        assertThat(foundBookmark.getIsActive()).isTrue();
    }

    @Test
    @DisplayName("북마크한 동아리 목록을 최신순으로 조회한다.")
    void findBookmarkedClubs() throws InterruptedException {
        // Given
        bookmarkRepository.save(Bookmark.create(user.getId(), club1.getId(), BookmarkType.CLUB));
        Thread.sleep(10); // 생성 시간 차이를 두기 위함
        bookmarkRepository.save(Bookmark.create(user.getId(), club2.getId(), BookmarkType.CLUB));

        // When
        var result = bookmarkQueryRepository.findBookmarkedClubs(user.getId(), PageRequest.of(0, 10));

        // Then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).clubId()).isEqualTo(club2.getId()); // club2가 최신
        assertThat(result.getContent().get(1).clubId()).isEqualTo(club1.getId());
    }

    @Test
    @DisplayName("북마크한 서류/면접 후기 목록을 조회한다.")
    void findBookmarkedInterviewReviews() {
        // Given
        bookmarkRepository.save(Bookmark.create(user.getId(), interviewReview.getId(), BookmarkType.INTERVIEW_REVIEW));
        bookmarkRepository.save(Bookmark.create(user.getId(), activityReview.getId(), BookmarkType.ACTIVITY_REVIEW));

        // When
        var result = bookmarkQueryRepository.findBookmarkedReviews(user.getId(), BookmarkType.INTERVIEW_REVIEW, PageRequest.of(0, 10));

        // Then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Interview Review");
    }

    @Test
    @DisplayName("북마크한 활동 후기 목록을 조회한다.")
    void findBookmarkedActivityReviews() {
        // Given
        bookmarkRepository.save(Bookmark.create(user.getId(), interviewReview.getId(), BookmarkType.INTERVIEW_REVIEW));
        bookmarkRepository.save(Bookmark.create(user.getId(), activityReview.getId(), BookmarkType.ACTIVITY_REVIEW));

        // When
        var result = bookmarkQueryRepository.findBookmarkedReviews(user.getId(), BookmarkType.ACTIVITY_REVIEW, PageRequest.of(0, 10));

        // Then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Activity Review");
    }

    @Test
    @DisplayName("북마크한 블로그 후기 목록을 조회한다.")
    void findBookmarkedBlogReviews() {
        // Given
        bookmarkRepository.save(Bookmark.create(user.getId(), blogReview.getId(), BookmarkType.BLOG_REVIEW));

        // When
        var result = bookmarkQueryRepository.findBookmarkedBlogReviews(user.getId(), PageRequest.of(0, 10));

        // Then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Blog Review");
    }
}