package com.moyeoit.domain.app_user.controller.response;

import com.moyeoit.domain.app_user.domain.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateResponse {

    private Long id;
    private boolean activate;
    private TermResponse term;

    public static ActivateResponse from(AppUser appUser, TermResponse term) {
        return new ActivateResponse(
                appUser.getId(),
                appUser.isActive(),
                term
        );
    }

    public static ActivateResponse from(AppUser appUser) {
        return new ActivateResponse(
                appUser.getId(),
                appUser.isActive(),
                null
        );
    }

}