package com.moyeoit.domain.user.service;

import com.moyeoit.domain.user.controller.request.ActivateRequest;
import com.moyeoit.domain.user.domain.Term;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.repository.TermRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TermService {

    private final TermRepository termRepository;

    public Term createTerm(User user, ActivateRequest request) {
        Term term = Term.builder()
                .user(user)
                .overAge(request.isOverAge())
                .termOfService(request.isAgreeTermsOfService())
                .privacyPolicy(request.isAgreePrivacyPolicy())
                .marketingPrivacy(request.isAgreeMarketingPrivacy())
                .eventNotification(request.isAgreeEventNotification())
                .build();

        return termRepository.save(term);
    }

    public Term getTerm(Long userId) {
        return termRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_TERM));
    }


}