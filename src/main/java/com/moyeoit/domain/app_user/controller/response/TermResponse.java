package com.moyeoit.domain.app_user.controller.response;

import com.moyeoit.domain.app_user.domain.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저가 동의한 약관 및 정책에 대한 Response
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermResponse {

    private Long id;
    private boolean isOverAge;
    private boolean agreeTermsOfService;
    private boolean agreePrivacyPolicy;
    private boolean agreeMarketingPrivacy;
    private boolean agreeEventNotification;

    public static TermResponse from(Term term) {
        return new TermResponse(
                term.getId(),
                term.isOverAge(),
                term.isTermOfService(),
                term.isPrivacyPolicy(),
                term.isMarketingPrivacy(),
                term.isEventNotification()
        );

    }

}