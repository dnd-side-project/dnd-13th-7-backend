package com.moyeoit.domain.app_user.repository;

import com.moyeoit.domain.app_user.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}