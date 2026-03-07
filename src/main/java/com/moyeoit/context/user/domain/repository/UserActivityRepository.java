package com.moyeoit.context.user.domain.repository;

import com.moyeoit.context.user.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    Optional<UserActivity> findByUserId(Long userId);

    @Modifying
    @Query("delete from UserActivity ua where ua.clubId = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
