package com.moyeoit.domain.user.service;

import com.moyeoit.domain.club.repository.ClubSubscribeRepository;
import com.moyeoit.domain.review.repository.ReviewLikeRepository;
import com.moyeoit.domain.user.controller.response.InterestsResponse;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.domain.user.repository.JobRepository;
import com.moyeoit.domain.user.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final TermRepository termRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ClubSubscribeRepository clubSubscribeRepository;

//    /**
//     * AppUser ID 기반 AppUser 조회
//     */
//    public AppUserDto getAppUser(Long id) {
//        AppUser user = userRepository.findById(id)
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
//
//        return AppUserDto.of(user);
//    }
//
//    /**
//     * OAuth2 기반 AppUser 생성
//     */
//    @Transactional
//    public AppUserDto findOrCreateAppUserFromOAuth2(OAuth2UserProfile profile) {
//        Optional<AppUser> findAppUser = userRepository.findByEmailAndProvider(profile.getEmail(),
//                profile.getProvider());
//        if (findAppUser.isPresent()) {
//            return AppUserDto.of(findAppUser.get());
//        }
//
//        AppUser user = AppUser.builder()
//                .name(profile.getName())
//                .email(profile.getEmail())
//                .provider(profile.getProvider())
//                .active(false)
//                .build();
//
//        AppUser savedUser = userRepository.save(user);
//        return AppUserDto.of(savedUser);
//    }
//
//    @Transactional
//    public AppUserDto activateUser(Long userId, ActivateRequest request) {
//        AppUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
//
//        Job job = jobRepository.findById(request.getJobId())
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_JOB));
//
//        user.activate(request.getNickname(), job);
//
//        // Term 데이터를 생성합니다.
//        Term term = Term.builder()
//                .user(user)
//                .overAge(request.isOverAge())
//                .termOfService(request.isAgreeTermsOfService())
//                .privacyPolicy(request.isAgreePrivacyPolicy())
//                .marketingPrivacy(request.isAgreeMarketingPrivacy())
//                .eventNotification(request.isAgreeEventNotification())
//                .build();
//
//        termRepository.save(term);
//
//        return AppUserDto.of(user);
//    }
//
//    /**
//     * 유저 활성 상태 조회
//     */
//    public ActivateResponse getActivateStatus(Long userId) {
//        AppUser appUser = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
//
//        if (appUser.isActive()) { // 활성 상태라면 Term 정보 조회
//            Term term = termRepository.findByUserId(userId)
//                    .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND_TERM));
//
//            return ActivateResponse.from(appUser, TermResponse.from(term));
//        }
//
//        return ActivateResponse.from(appUser);
//    }
//
//    public AppUserDto getProfile(Long userId) {
//        AppUser user = userRepository.findByIdWithJob(userId)
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
//
//        return AppUserDto.of(user);
//    }
//
//    @Transactional
//    public AppUserDto updateProfileImage(Long userId, String profileImageUrl) {
//        AppUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
//
//        user.setProfileImageUrl(profileImageUrl);
//
//        return AppUserDto.of(user);
//    }

    /**
     * 공고 구독 개수 추천 후기 개수
     */
    public InterestsResponse getInterests(Long userId) {
        Long likeCount = reviewLikeRepository.countByUserId(userId);

        Long subscribeCount = clubSubscribeRepository.countByUserId(userId);

        return new InterestsResponse(likeCount, subscribeCount);
    }


}