package com.moyeoit.domain.user.service;

import com.moyeoit.domain.club.repository.ClubSubscribeRepository;
import com.moyeoit.domain.review.repository.ReviewLikeRepository;
import com.moyeoit.domain.user.controller.request.ActivateRequest;
import com.moyeoit.domain.user.controller.response.ActivateResponse;
import com.moyeoit.domain.user.controller.response.InterestsResponse;
import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.Term;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.domain.user.infra.query.QueryUserRepository;
import com.moyeoit.domain.user.repository.JobRepository;
import com.moyeoit.domain.user.service.dto.UserDto;
import com.moyeoit.domain.user.service.dto.UserProfileResponse;
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

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        log.info("{}", id);
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

        userRepository.save(newUser);
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

    @Transactional
    public UserDto updateProfileImage(Long userId, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        user.updateProfileImage(profileImageUrl);
        return UserDto.of(user);
    }

    public InterestsResponse getInterests(Long userId) {
        Long likeCount = reviewLikeRepository.countByUserId(userId);

        Long subscribeCount = clubSubscribeRepository.countByUserId(userId);

        return new InterestsResponse(likeCount, subscribeCount);
    }


}