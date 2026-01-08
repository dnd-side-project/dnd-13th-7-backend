package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.schedule.ClubSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubScheduleRepository extends JpaRepository<ClubSchedule,Long> {
}
