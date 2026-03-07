package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.ClubRecruitmentPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRecruitmentPartRepository extends JpaRepository<ClubRecruitmentPart, Long> {

    @Modifying
    @Query("delete from ClubRecruitmentPart crp where crp.clubRecruitment.club.id = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
