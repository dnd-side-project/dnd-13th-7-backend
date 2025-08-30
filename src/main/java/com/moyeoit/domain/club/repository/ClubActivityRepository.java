package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.entity.ClubActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity,Long> {
}
