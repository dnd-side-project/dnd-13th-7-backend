package com.moyeoit.auth.controller.response;

import com.moyeoit.domain.app_user.domain.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUriResponse {

    private String url;
    private String state;
    private AuthProvider provider;

}
