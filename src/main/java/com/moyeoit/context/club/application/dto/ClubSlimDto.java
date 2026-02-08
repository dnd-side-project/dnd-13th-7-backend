package com.moyeoit.context.club.application.dto;

import com.moyeoit.context.club.domain.entity.Club;
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
                club.getClubProfile().slogan(),
                club.getClubProfile().bio(),
                club.getClubProfile().establishment().getYear(),
                club.getClubProfile().totalParticipant(),
                club.getClubProfile().operation(),
                club.getClubActivityMethod().offline(),
                club.getClubActivityMethod().online(),
                club.getClubPlace().location(),
                club.getClubPlace().address(),
                club.getRecruiting(),
                club.getClubProfile().imageUrl()
        );
    }

}