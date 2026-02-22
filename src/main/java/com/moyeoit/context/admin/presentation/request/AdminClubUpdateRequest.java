package com.moyeoit.context.admin.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminClubUpdateRequest {
    private String name;
    private String slogan;
    private String bio;
    private Integer establishmentYear;
    private Integer totalParticipant;
    private Integer operation;
    private String online;
    private String offline;
    private String location;
    private String address;
    private Boolean recruiting;
    private String imageUrl;
    private String significant;
}
