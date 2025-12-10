package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.domain.entity.process.ClubProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessRepository extends JpaRepository<ClubProcess, Long> {
}