package com.moyeoit.domain.app_user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterestsResponse {

    @JsonProperty("like_count")
    private Long likeCount;

    @JsonProperty("club_subscribe_count")
    private Long clubSubscribeCount;

}
