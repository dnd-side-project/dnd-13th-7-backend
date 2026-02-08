package com.moyeoit.context.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_user_activity")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_activity_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "generation")
    private Integer generation;

    @Column(name = "activity_start_date")
    private LocalDateTime startDate;
    @Column(name = "activity_end_date")
    private LocalDateTime endDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "certify")
    private boolean certify;

    public void updateActivity(Long clubId, Long jobId, Integer generation, boolean active) {
        this.clubId = clubId;
        this.jobId = jobId;
        this.generation = generation;
        this.active = active;
    }

    public void updatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜(" + startDate + ")는 종료 날짜(" + endDate + ")보다 이전이어야 합니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateActive(boolean condition) {
        this.active = condition;
    }

    public void updateCertify(boolean condition) {
        this.certify = condition;
    }

}