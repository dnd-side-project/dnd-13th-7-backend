package com.moyeoit.domain.review.controller.response.v2;

import com.moyeoit.domain.club.dto.ClubWithNameAndImageUrlDto;
import com.moyeoit.domain.user.service.dto.JobDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDetailView {

    private JobDto job;
    private ClubWithNameAndImageUrlDto club;
    private Integer generation;
}