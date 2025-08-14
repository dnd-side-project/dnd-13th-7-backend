package com.moyeoit.domain.app_user.service;

import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.app_user.repository.JobRepository;
import com.moyeoit.domain.app_user.service.dto.JobsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public JobsDto getJobs() {
        List<Job> jobs = jobRepository.findAll();
        return JobsDto.of(jobs);
    }

}