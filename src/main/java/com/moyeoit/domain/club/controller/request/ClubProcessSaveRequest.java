package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.Process;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubProcessSaveRequest {
    private String processDescription;
    private Integer sequence;
    private Long clubId;

    public static Process of(ClubProcessSaveRequest request, Club club){
        return Process.builder()
                .club(club)
                .processDescription(request.getProcessDescription())
                .sequence(request.getSequence())
                .build();
    }
}
