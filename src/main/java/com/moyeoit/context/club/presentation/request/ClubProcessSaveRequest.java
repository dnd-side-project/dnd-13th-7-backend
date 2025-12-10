package com.moyeoit.context.club.presentation.request;

import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.process.ClubProcess;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubProcessSaveRequest {
    private String processDescription;
    private Integer sequence;
    private Long clubId;

    public static ClubProcess of(ClubProcessSaveRequest request, Club club){
        return ClubProcess.builder()
                .club(club)
                .description(request.getProcessDescription())
                .sequence(request.getSequence())
                .build();
    }
}
