package com.moyeoit.context.user.service;

import com.moyeoit.context.user.controller.request.UserActivityUpdateRequest;
import com.moyeoit.context.user.domain.UserActivity;
import com.moyeoit.context.user.domain.repository.UserActivityRepository;
import com.moyeoit.global.exception.code.UserErrorCode;
import com.moyeoit.global.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Transactional
    public void updateUserActivity(Long id, UserActivityUpdateRequest request) {
        UserActivity userActivity = userActivityRepository.findById(id)
                .orElseThrow(() -> new AppException(UserErrorCode.USER_ACTIVITY_NOT_FOUND));

        userActivity.updateActivity(request.getClubId(), request.getJobId(), request.getGeneration(), request.isActive());
        userActivity.updatePeriod(request.getStartDate(), request.getEndDate());

        userActivityRepository.save(userActivity);
    }
}
