package com.moyeoit.context.club.repository;

import com.moyeoit.context.club.entity.schedule.ClubSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubScheduleRepository extends JpaRepository<ClubSchedule,Long> {
}
