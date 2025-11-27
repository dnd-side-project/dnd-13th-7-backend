package com.moyeoit.context.club.repository;

import com.moyeoit.context.club.entity.activity.ClubActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity,Long> {
}
