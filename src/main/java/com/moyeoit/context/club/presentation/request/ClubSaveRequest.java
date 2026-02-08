package com.moyeoit.context.club.presentation.request;

import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.ClubActivityMethod;
import com.moyeoit.context.club.domain.entity.ClubPlace;
import com.moyeoit.context.club.domain.entity.ClubProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClubSaveRequest {
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

    public static Club of(ClubSaveRequest request){
        ClubProfile clubProfile = new ClubProfile(request.getSlogan(),
                request.getBio(),
                LocalDate.of(request.getEstablishment(), 1, 1),
                request.getTotalParticipant(),
                request.getOperation(),
                request.getImageUrl()
                );

        ClubActivityMethod method = new ClubActivityMethod(
                request.getOnline(),
                request.getOffline()
        );

        ClubPlace address = new ClubPlace(
                request.getLocation(),
                request.getAddress()
        );

        return Club.builder()
                .name(request.getName())
                .clubProfile(clubProfile)
                .ClubActivityMethod(method)
                .clubPlace(address)
                .recruiting(request.getRecruiting())
                .build();
    }
}
