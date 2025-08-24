package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom{

    @Query("SELECT s.club FROM ClubSubscribe s WHERE s.user.id = :userId")
    Page<Club> findSubscribedClubs(@Param("userId") Long userId, Pageable pageable);

}
