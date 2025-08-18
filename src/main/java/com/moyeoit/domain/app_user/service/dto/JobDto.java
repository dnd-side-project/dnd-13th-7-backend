package com.moyeoit.domain.app_user.service.dto;

import com.moyeoit.domain.app_user.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long id;
    private String name;
    private String engName;

    public static JobDto of(Job job) {
        return new JobDto(job.getId(),
                job.getName(),
                job.getEngName());
    }

}