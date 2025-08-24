package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.controller.response.ClubFindListResponse;
import com.moyeoit.domain.club.entity.ClubKeyword;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubKeywordRepository extends JpaRepository<ClubKeyword,Long> {
    Optional<ClubKeyword> findClubKeywordByContent(String content);
}
