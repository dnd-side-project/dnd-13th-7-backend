package com.moyeoit.context.user.domain;

import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "deleted_date", nullable = true)
    private LocalDateTime deletedDate;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "subscription_email")
    private String subscriptionEmail;

    @Column(name = "email_notify_agree")
    private boolean emailNotifyAgree;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    public void activate(String nickname, Long jobId) {
        this.nickname = nickname;
        this.jobId = jobId;
        this.active = true;
    }

    public void update(String nickname, Long jobId, UserStatus status) {
        this.nickname = nickname;
        this.jobId = jobId;
        this.status = status;
    }

    public void updateAccountManage(String name,
                                    String subscriptionEmail,
                                    boolean emailNotifyAgree) {
        this.name = name;
        this.subscriptionEmail = subscriptionEmail;
        this.emailNotifyAgree = emailNotifyAgree;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
