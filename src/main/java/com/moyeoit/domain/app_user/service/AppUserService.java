package com.moyeoit.domain.app_user.service;

import com.moyeoit.domain.app_user.controller.request.ActivateRequest;
import com.moyeoit.domain.app_user.controller.response.ActivateResponse;
import com.moyeoit.domain.app_user.controller.response.TermResponse;
import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.app_user.domain.Term;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.app_user.repository.JobRepository;
import com.moyeoit.domain.app_user.repository.TermRepository;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.extractor.OAuth2UserProfile;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final JobRepository jobRepository;
    private final TermRepository termRepository;

    /**
     * AppUser ID 기반 AppUser 조회
     */
    public AppUserDto getAppUser(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        return AppUserDto.of(user);
    }

    /**
     * OAuth2 기반 AppUser 생성
     */
    @Transactional
    public AppUserDto findOrCreateAppUserFromOAuth2(OAuth2UserProfile profile) {
        Optional<AppUser> findAppUser = appUserRepository.findByEmailAndProvider(profile.getEmail(),
                profile.getProvider());
        if (findAppUser.isPresent()) {
            return AppUserDto.of(findAppUser.get());
        }

        AppUser user = AppUser.builder()
                .name(profile.getName())
                .email(profile.getEmail())
                .provider(profile.getProvider())
                .active(false)
                .build();

        AppUser savedUser = appUserRepository.save(user);
        return AppUserDto.of(savedUser);
    }

    @Transactional
    public AppUserDto activateUser(Long userId, ActivateRequest request) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_JOB));

        user.activate(request.getNickname(), job);

        // Term 데이터를 생성합니다.
        Term term = Term.builder()
                .overAge(request.isOverAge())
                .termOfService(request.isAgreeTermsOfService())
                .privacyPolicy(request.isAgreePrivacyPolicy())
                .marketingPrivacy(request.isAgreeMarketingPrivacy())
                .eventNotification(request.isAgreeEventNotification())
                .build();

        termRepository.save(term);

        return AppUserDto.of(user);
    }

    /**
     * 유저 활성 상태 조회
     */
    public ActivateResponse getActivateStatus(Long userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        if (appUser.isActive()) { // 활성 상태라면 Term 정보 조회
            Term term = termRepository.findByUserId(userId)
                    .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_TERM));

            return ActivateResponse.from(appUser, TermResponse.from(term));
        }

        return ActivateResponse.from(appUser);
    }

}