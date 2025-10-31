package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.process.ClubProcess;
import java.util.List;
import lombok.Builder;

@Builder
public record ClubDto(
        String name,
        String slogan,
        String bio,
        Integer establishment,
        Integer totalParticipant,
        Integer operation,
        String offline,
        String online,
        String significant,
        String location,
        String address,
        Boolean recruiting,
        String imageUrl,
        List<String> process) {

    public static ClubDto from(Club entity) {
        return ClubDto.builder()
                .name(entity.getName())
                .slogan(entity.getClubProfile().getSlogan())
                .bio(entity.getClubProfile().getBio())
                .establishment(entity.getClubProfile().getEstablishment().getYear())
                .totalParticipant(entity.getClubProfile().getTotalParticipant())
                .operation(entity.getClubProfile().getOperation())
                .offline(entity.getClubMethod().getOffline())
                .online(entity.getClubMethod().getOnline())
                .significant(entity.getSignificant())
                .location(entity.getClubAddress().getLocation())
                .address(entity.getClubAddress().getAddress())
                .recruiting(entity.getRecruiting())
                .imageUrl(entity.getClubProfile().getImageUrl())
                .process(entity.getProcesses().stream().map(ClubProcess::getDescription).toList())
                .build();
    }
}
