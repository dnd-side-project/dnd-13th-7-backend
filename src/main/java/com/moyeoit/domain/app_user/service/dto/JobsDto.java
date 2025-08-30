package com.moyeoit.domain.app_user.service.dto;

import com.moyeoit.domain.app_user.domain.Job;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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