package com.moyeoit.domain.user.service.dto;

import com.moyeoit.domain.user.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobsDto {

    private List<JobDto> jobs;

    public static JobsDto of(List<Job> jobs) {
        List<JobDto> argJobs = jobs.stream()
                .map(JobDto::of)
                .toList();
        return new JobsDto(argJobs);
    }

}