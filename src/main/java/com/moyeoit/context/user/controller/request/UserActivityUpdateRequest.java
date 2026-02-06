package com.moyeoit.context.user.controller.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserActivityUpdateRequest {
    @NotNull(message = "클럽 ID는 필수입니다.")
    private Long clubId;
    @NotNull(message = "직무 ID는 필수입니다.")
    private Long jobId;
    @NotNull(message = "기수는 필수입니다.")
    private Integer generation;
    @NotNull(message = "시작일은 필수입니다.")
    private LocalDateTime startDate;
    @NotNull(message = "종료일은 필수입니다.")
    private LocalDateTime endDate;
}
