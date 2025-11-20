package com.moyeoit.domain.auth.presentation.response;

import com.moyeoit.domain.user.domain.AuthProvider;
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
