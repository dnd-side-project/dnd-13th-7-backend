package com.moyeoit.context.user.controller.response;

import java.time.LocalDateTime;

public record UserActivityResponse(

        Long id,
        Long userId,
        String clubName,
        String jobName,
        Integer generation,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean active
) {}
