package com.moyeoit.global.auth.extractor;

import com.moyeoit.domain.app_user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2UserProfile {

    private String name;
    private String email;
    private AuthProvider provider;

}