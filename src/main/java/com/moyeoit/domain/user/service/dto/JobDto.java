package com.moyeoit.domain.user.service.dto;

import com.moyeoit.domain.user.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDto {

    private Long id;
    private String name;
    private String engName;

    public JobDto(Long id, String name, String engName) {
        this.id = id;
        this.name = name;
        this.engName = engName;
    }

    public static JobDto of(Job job) {
        return new JobDto(job.getId(),
                job.getName(),
                job.getEngName());
    }

    public static JobDto ofNullable(Job job) {
        if (job == null || job.getId() == null) {
            return null;
        }
        return JobDto.of(job);
    }

}