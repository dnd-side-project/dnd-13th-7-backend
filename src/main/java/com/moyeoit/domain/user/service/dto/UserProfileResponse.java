package com.moyeoit.domain.user.service.dto;

import com.moyeoit.domain.user.domain.AuthProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private JobDto job;
    private AuthProvider authProvider;
    private Boolean active;

    public UserProfileResponse(Long id, String name, String email, String nickname, String profileImageUrl, JobDto job, AuthProvider authProvider, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.job = job;
        this.authProvider = authProvider;
        this.active = active;
    }

}
