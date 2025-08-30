package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.entity.ClubRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRecruitmentRepository extends JpaRepository<ClubRecruitment,Long> {
}
