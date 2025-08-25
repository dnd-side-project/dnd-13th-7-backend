package com.moyeoit.domain.club.controller.request;

import com.moyeoit.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
        return Club.builder()
                .name(request.getName())
                .slogan(request.getSlogan())
                .bio(request.getBio())
                .establishment(request.getEstablishment())
                .totalParticipant(request.getTotalParticipant())
                .operation(request.getOperation())
                .offline(request.getOffline())
                .online(request.getOnline())
                .location(request.getLocation())
                .address(request.getAddress())
                .recruiting(request.getRecruiting())
                .imageUrl(request.getImageUrl())
                .build();
    }
}
