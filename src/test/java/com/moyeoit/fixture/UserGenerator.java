package com.moyeoit.fixture;

import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;

public class UserGenerator {

    /**
     * 활성화 된 유저를 생성합니다.
     */
    public static User createActivatedUser(Job job) {
        return User.builder()
                .name("홍길동")
                .email("test@test.com")
                .nickname("홍길동 닉네임")
                .profileImageUrl("")
                .provider(AuthProvider.GOOGLE)
                .active(true)
                .jobId(job.getId())
                .deleted(false)
                .build();
    }

    public static User createNonActivateUser() {
        return User.builder()
                .name("홍길동")
                .email("test@test.com")
                .profileImageUrl("")
                .provider(AuthProvider.GOOGLE)
                .active(false)
                .deleted(false)
                .build();
    }

}