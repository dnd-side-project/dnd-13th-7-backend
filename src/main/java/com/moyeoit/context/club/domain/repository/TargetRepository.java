package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {

    @Modifying
    @Query("delete from Target t where t.club.id = :clubId")
    void deleteAllByClubId(@Param("clubId") Long clubId);
}
