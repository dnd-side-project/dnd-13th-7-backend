package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.activity.ClubActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity,Long> {

    @Modifying
    @Query("delete from ClubActivity ca where ca.club.id = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
