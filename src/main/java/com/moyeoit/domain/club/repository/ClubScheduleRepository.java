package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.entity.ClubSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubScheduleRepository extends JpaRepository<ClubSchedule,Long> {
}
