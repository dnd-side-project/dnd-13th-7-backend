package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.ClubRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRecruitmentRepository extends JpaRepository<ClubRecruitment,Long> {

    @Modifying
    @Query("delete from ClubRecruitment cr where cr.club.id = :clubId")
    void deleteByClubId(@Param("clubId") Long clubId);
}
