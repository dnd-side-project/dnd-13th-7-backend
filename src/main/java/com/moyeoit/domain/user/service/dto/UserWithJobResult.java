package com.moyeoit.domain.user.service.dto;

import com.moyeoit.domain.user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithJobResult {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private JobDto job;
    private AuthProvider provider;
    private boolean active;

}
