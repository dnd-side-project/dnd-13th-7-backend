package com.moyeoit.domain.user.service;

import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.repository.JobRepository;
import com.moyeoit.domain.user.service.dto.JobsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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