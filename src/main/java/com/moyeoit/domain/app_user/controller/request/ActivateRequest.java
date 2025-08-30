package com.moyeoit.domain.app_user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("job_id")
    private Long jobId;

    @JsonProperty("is_over_age")
    private boolean isOverAge;

    @JsonProperty("agree_terms_of_service")
    private boolean agreeTermsOfService;

    @JsonProperty("agree_privacy_policy")
    private boolean agreePrivacyPolicy;

    @JsonProperty("agree_marketing_privacy")
    private boolean agreeMarketingPrivacy;

    @JsonProperty("agree_event_notification")
    private boolean agreeEventNotification;

}