package com.moyeoit.context.club.repository;

import com.moyeoit.context.club.entity.ClubRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRecruitmentRepository extends JpaRepository<ClubRecruitment,Long> {
}
