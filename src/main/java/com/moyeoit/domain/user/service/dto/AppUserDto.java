package com.moyeoit.domain.user.service.dto;

import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private JobDto jobDto;
    private AuthProvider provider;
    private boolean active;

    public static AppUserDto of(User user) {
        return new AppUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl(),
                JobDto.ofNullable(new Job()),
                user.getProvider(),
                user.isActive());
    }

//    public static AppUserDto of(User user) {
//        return new AppUserDto(
//                user.getId(),
//                user.getName(),
//                user.getEmail(),
//                user.getNickname(),
//                user.getProfileImageUrl(),
//                user.getJobId(),
//                user.getProvider(),
//                user.isActive());
//    }

}