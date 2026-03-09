package com.moyeoit.context.club.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moyeoit.CoreDbContextTest;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.presentation.request.ClubPagingRequest;
import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.domain.repository.BookmarkRepository;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.infra.jpa.JpaUserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class ClubRepositoryImplTest extends CoreDbContextTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    @DisplayName("로그인 사용자의 동아리 목록은 북마크, 모집중 여부 순으로 우선 조회된다.")
    void findClubListPrioritizesBookmarkedAndRecruitingClubsForAuthenticatedUser() {
        User user = userRepository.save(User.builder()
                .name("testUser")
                .email("test@test.com")
                .provider(AuthProvider.KAKAO)
                .active(true)
                .build());

        Club club1 = clubRepository.save(Club.builder().name("Club 1").recruiting(false).build());
        Club club2 = clubRepository.save(Club.builder().name("Club 2").recruiting(false).build());
        Club club3 = clubRepository.save(Club.builder().name("Club 3").recruiting(true).build());
        Club club4 = clubRepository.save(Club.builder().name("Club 4").recruiting(true).build());

        bookmarkRepository.save(Bookmark.create(user.getId(), club1.getId(), BookmarkType.CLUB));
        bookmarkRepository.save(Bookmark.create(user.getId(), club4.getId(), BookmarkType.CLUB));

        ClubPagingRequest request = new ClubPagingRequest();
        request.setSort("최신순");

        List<Long> clubIds = clubRepository.findClubByRequest(request, PageRequest.of(0, 10), user.getId())
                .getContent()
                .stream()
                .map(Club::getId)
                .toList();

        assertThat(clubIds).containsExactly(club4.getId(), club1.getId(), club3.getId(), club2.getId());
    }

    @Test
    @DisplayName("비로그인 사용자의 동아리 목록은 모집중인 동아리가 먼저 조회된다.")
    void findClubListPrioritizesRecruitingClubsForAnonymousUser() {
        Club club1 = clubRepository.save(Club.builder().name("Club 1").recruiting(false).build());
        Club club2 = clubRepository.save(Club.builder().name("Club 2").recruiting(true).build());
        Club club3 = clubRepository.save(Club.builder().name("Club 3").recruiting(false).build());
        Club club4 = clubRepository.save(Club.builder().name("Club 4").recruiting(true).build());

        ClubPagingRequest request = new ClubPagingRequest();
        request.setSort("최신순");

        List<Long> clubIds = clubRepository.findClubByRequest(request, PageRequest.of(0, 10), null)
                .getContent()
                .stream()
                .map(Club::getId)
                .toList();

        assertThat(clubIds).containsExactly(club4.getId(), club2.getId(), club3.getId(), club1.getId());
    }
}
