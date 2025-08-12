package com.moyeoit.domain.club.dto;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.entity.Process;
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
        String location,
        String address,
        boolean recruiting,
        String imageUrl,
        List<String> process) {

    public static ClubDto from(Club entity) {
        return ClubDto.builder()
                .name(entity.getName())
                .slogan(entity.getSlogan())
                .bio(entity.getBio())
                .establishment(entity.getEstablishment())
                .totalParticipant(entity.getTotal_participant())
                .operation(entity.getOperation())
                .offline(entity.getOffline())
                .online(entity.getOnline())
                .location(entity.getLocation())
                .address(entity.getAddress())
                .recruiting(entity.isRecruiting())
                .imageUrl(entity.getImage_url())
                .process(entity.getProcess().stream().map(Process::getProcess_description).toList())
                .build();
    }
}
