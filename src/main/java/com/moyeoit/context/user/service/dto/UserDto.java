package com.moyeoit.context.user.service.dto;

import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private Long jobId;
    private AuthProvider provider;
    private boolean active;

    public static UserDto of(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getJobId(),
                user.getProvider(),
                user.isActive());
    }

}