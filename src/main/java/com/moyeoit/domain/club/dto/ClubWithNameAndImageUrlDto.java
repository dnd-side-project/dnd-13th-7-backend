package com.moyeoit.domain.club.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubWithNameAndImageUrlDto {

    private String clubName;
    private String imageUrl;

}
