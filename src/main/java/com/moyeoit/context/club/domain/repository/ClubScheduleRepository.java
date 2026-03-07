package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.schedule.ClubSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubScheduleRepository extends JpaRepository<ClubSchedule,Long> {

    @Modifying
    @Query("delete from ClubSchedule cs where cs.club.id = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
