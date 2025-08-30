package com.moyeoit.domain.app_user.service.dto;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.AuthProvider;
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

    public static AppUserDto of(AppUser user) {
        return new AppUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl(),
                JobDto.ofNullable(user.getJob()),
                user.getProvider(),
                user.isActive());
    }

}