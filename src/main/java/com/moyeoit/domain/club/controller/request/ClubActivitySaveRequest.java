package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class ClubActivitySaveRequest {
    private Long clubId;
    private String hashtag;
    private String activityName;
    private String activityDescribe;
    private String imageUrl;
    private Integer activityOrder;

    public static ClubActivity of(ClubActivitySaveRequest request,Club club) {
        return ClubActivity.builder()
                .club(club)
                .hashtag(request.getHashtag())
                .activityName(request.getActivityName())
                .activityDescribe(request.getActivityDescribe())
                .imageUrl(request.getImageUrl())
                .activityOrder(request.getActivityOrder())
                .build();
    }
}
