package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.position.ClubPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubPositionRepository extends JpaRepository<ClubPosition, Long> {

    @Modifying
    @Query("delete from ClubPosition cp where cp.club.id = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
