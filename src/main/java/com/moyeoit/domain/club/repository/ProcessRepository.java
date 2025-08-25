package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
}