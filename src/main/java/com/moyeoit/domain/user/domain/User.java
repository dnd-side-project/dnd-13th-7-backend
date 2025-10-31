package com.moyeoit.domain.user.domain;

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

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "deleted_date", nullable = true)
    private LocalDateTime deletedDate;

    @Column(name = "deleted")
    private Boolean deleted;

    public void activate(String nickname, Long jobId) {
        this.nickname = nickname;
        this.jobId = jobId;
        this.active = true;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}