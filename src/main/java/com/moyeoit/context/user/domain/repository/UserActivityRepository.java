package com.moyeoit.context.user.domain.repository;

import com.moyeoit.context.user.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
}
