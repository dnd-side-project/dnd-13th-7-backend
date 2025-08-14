package com.moyeoit.domain.app_user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateRequest {

    private String nickname;
    private Long jobId;
    private boolean isOverAge;
    private boolean agreeTermsOfService;
    private boolean agreePrivacyPolicy;
    private boolean agreeMarketingPrivacy;
    private boolean agreeEventNotification;

}