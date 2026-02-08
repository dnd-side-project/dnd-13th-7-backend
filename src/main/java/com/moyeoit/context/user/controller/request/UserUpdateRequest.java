package com.moyeoit.context.user.controller.request;

import com.moyeoit.context.user.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String nickname;
    private Long jobId;
    private UserStatus status;

}
