package com.moyeoit.context.user.domain.repository;

import com.moyeoit.context.user.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    Optional<UserActivity> findByUserId(Long userId);
}
