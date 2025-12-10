package com.moyeoit.context.club.presentation.request;

import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.activity.ClubActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
                .name(request.getActivityName())
                .description(request.getActivityDescribe())
                .imageUrl(request.getImageUrl())
                .sequence(request.getActivityOrder())
                .build();
    }
}
