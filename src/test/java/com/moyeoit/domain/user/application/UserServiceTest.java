package com.moyeoit.domain.user.application;

import com.moyeoit.CoreDbContextTest;
import com.moyeoit.domain.user.controller.request.ActivateRequest;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.infra.jpa.JpaUserRepository;
import com.moyeoit.domain.user.repository.JobRepository;
import com.moyeoit.domain.user.service.UserService;
import com.moyeoit.domain.user.service.dto.UserDto;
import com.moyeoit.fixture.JobGenerator;
import com.moyeoit.fixture.UserGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends CoreDbContextTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("프로필 이미지를 변경합니다.")
    void updateProfileImage() {
        // Given
        Job createdJob = jobRepository.save(JobGenerator.createJob("개발자", "developer"));
        User createdUser = jpaUserRepository.save(UserGenerator.createActivatedUser(createdJob));
        String testImageUrl = "https://test.com/test_image_url.jpg";

        // When
        UserDto userDto = userService.updateProfileImage(createdUser.getId(), testImageUrl);

        // Then
        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getId()).isEqualTo(createdUser.getId());
        Assertions.assertThat(userDto.getProfileImageUrl()).isEqualTo(testImageUrl);
    }

    @Test
    @DisplayName("유저를 활성 상태로 변경합니다.")
    void activateUserTest() {
        // Given
        jobRepository.save(JobGenerator.createJob("개발자", "developer"));
        User createdUser = jpaUserRepository.save(UserGenerator.createNonActivateUser());
        ActivateRequest activateRequest = new ActivateRequest("홍길동 닉네임", createdUser.getId(), true, true, true, true, true);


        // When
        userService.activateUser(createdUser.getId(), activateRequest);
        User user = jpaUserRepository.findById(createdUser.getId()).get();


        // then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(createdUser.getId());
        Assertions.assertThat(user.isActive()).isTrue();
        Assertions.assertThat(user.getNickname()).isEqualTo(activateRequest.getNickname());
    }

}