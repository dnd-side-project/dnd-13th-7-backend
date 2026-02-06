package com.moyeoit.context.user.controller.response;

import com.moyeoit.context.user.domain.User;

public record UserManageResponse(
        String name,
        String subscriptionEmail,
        boolean emailNotifyAgree
) {

    public static UserManageResponse of(User user) {
        return new UserManageResponse(user.getName(), user.getSubscriptionEmail(), user.isEmailNotifyAgree());
    }

}