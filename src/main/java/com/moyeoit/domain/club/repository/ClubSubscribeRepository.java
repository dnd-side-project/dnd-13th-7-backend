package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubSubscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubSubscribeRepository extends JpaRepository<ClubSubscribe, Long> {
    Optional<ClubSubscribe> findByUserAndClub(AppUser user, Club club);

    @Query("SELECT count(cs) FROM ClubSubscribe cs WHERE cs.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
}
