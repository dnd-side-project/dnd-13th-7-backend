package com.moyeoit.context.user.service;

import com.moyeoit.context.club.domain.repository.ClubSubscribeRepository;
import com.moyeoit.context.deprecated.review.repository.ReviewLikeRepository;
import com.moyeoit.context.user.controller.request.AccountManageUpdateRequest;
import com.moyeoit.context.user.controller.request.ActivateRequest;
import com.moyeoit.context.user.controller.request.UserUpdateRequest;
import com.moyeoit.context.user.controller.response.ActivateResponse;
import com.moyeoit.context.user.controller.response.InterestsResponse;
import com.moyeoit.context.user.controller.response.UserManageResponse;
import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.Term;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.UserActivity;
import com.moyeoit.context.user.domain.repository.UserActivityRepository;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.context.user.infra.query.QueryUserRepository;
import com.moyeoit.context.user.repository.JobRepository;
import com.moyeoit.context.user.service.dto.UserDto;
import com.moyeoit.context.user.service.dto.UserProfileResponse;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final TermService termService;
    private final QueryUserRepository queryUserRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ClubSubscribeRepository clubSubscribeRepository;
    private final UserActivityRepository userActivityRepository;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        return UserDto.of(user);
    }

    @Transactional
    public UserDto findOrCreateUserFromOAuth(String name, String email, AuthProvider provider) {
        Optional<User> user = userRepository.findByEmailAndProvider(email, provider);

        if (user.isPresent()) {
            return UserDto.of(user.get());
        }

        User newUser = User.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .active(false)
                .deleted(false)
                .build();
        Long userId = userRepository.save(newUser);

        UserActivity userActivity = UserActivity.builder()
                .userId(userId)
                .active(false)
                .certify(false)
                .build();
        userActivityRepository.save(userActivity);

        return UserDto.of(newUser);
    }

    public UserDto activateUser(Long userId, ActivateRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        if (!jobRepository.existsById(req.getJobId()))
            throw new AppException(UserErrorCode.NOT_FOUND_JOB);

        user.activate(req.getNickname(), req.getJobId());
        termService.createTerm(user, req);
        return UserDto.of(user);
    }

    public ActivateResponse getActivateStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        if (user.isActive()) {
            Term termOfUser = termService.getTerm(user.getId());

            return ActivateResponse.from(user, termOfUser);
        }

        return ActivateResponse.from(user);
    }

    public UserProfileResponse getProfile(Long userId) {
        return queryUserRepository.findUserWithJob(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserManageResponse getUserManagerInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));
        return UserManageResponse.of(user);
    }

    @Transactional
    public UserDto updateProfileImage(Long userId, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        user.updateProfileImage(profileImageUrl);
        return UserDto.of(user);
    }

    @Transactional
    public void updateAccount(AccountManageUpdateRequest req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        user.updateAccountManage(req.getName(), req.getSubscriptionEmail(), req.isEmailAgree());
    }

    @Transactional
    public void updateUserInfo(UserUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        boolean existsNickname = userRepository.existsByNickname(request.getNickname());
        if (existsNickname) throw new AppException(UserErrorCode.DUPLICATE_NICKNAME);

        user.update(request.getNickname(), request.getJobId(), request.getStatus());
    }


    public InterestsResponse getInterests(Long userId) {
        Long likeCount = reviewLikeRepository.countByUserId(userId);

        Long subscribeCount = clubSubscribeRepository.countByUserId(userId);

        return new InterestsResponse(likeCount, subscribeCount);
    }


}