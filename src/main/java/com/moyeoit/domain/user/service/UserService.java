package com.moyeoit.domain.user.service;

import com.moyeoit.domain.user.controller.request.ActivateRequest;
import com.moyeoit.domain.user.controller.response.ActivateResponse;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final TermService termService;
    private final QueryUserRepository queryUserRepository;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        return UserDto.of(user);
    }

    @Transactional
    public UserDto updateProfileImage(Long userId, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        user.updateProfileImage(profileImageUrl);
        return UserDto.of(user);
    }

    /**
     * 유저 활성화
     *
     * @param userId 유저 ID
     * @param req    활성 요청 객체
     * @return
     */
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


}