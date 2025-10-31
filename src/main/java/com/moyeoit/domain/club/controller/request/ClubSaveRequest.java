package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.ClubAddress;
import com.moyeoit.domain.club.entity.ClubMethod;
import com.moyeoit.domain.club.entity.ClubProfile;
import com.moyeoit.domain.club.entity.schedule.ClubSchedule;
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

        ClubMethod method = new ClubMethod(
                request.getOnline(),
                request.getOffline()
        );

        ClubAddress address = new ClubAddress(
                request.getLocation(),
                request.getAddress()
        );

        return Club.builder()
                .name(request.getName())
                .clubProfile(clubProfile)
                .clubMethod(method)
                .clubAddress(address)
                .recruiting(request.getRecruiting())
                .build();
    }
}
