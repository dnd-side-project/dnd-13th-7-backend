package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.activity.ClubActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity,Long> {
}
