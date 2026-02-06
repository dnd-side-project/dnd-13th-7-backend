package com.moyeoit.context.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountManageUpdateRequest {

    private String name;
    private String subscriptionEmail;
    private boolean emailAgree;

}