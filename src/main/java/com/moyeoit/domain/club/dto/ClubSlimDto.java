package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubSlimDto {

    private Long id;
    private String name;
    private String slogan;
    private String bio;
    private Integer establishment;
    private Integer totalParticipant;
    private Integer operation;
    private String offline;
    private String online;
    private String location;
    private String address;
    private Boolean recruiting;
    private String imageUrl;

    public static ClubSlimDto from(Club club) {
        return new ClubSlimDto(
                club.getId(),
                club.getName(),
                club.getSlogan(),
                club.getBio(),
                club.getEstablishment(),
                club.getTotalParticipant(),
                club.getOperation(),
                club.getOffline(),
                club.getOnline(),
                club.getLocation(),
                club.getAddress(),
                club.getRecruiting(),
                club.getImageUrl()
        );
    }

}