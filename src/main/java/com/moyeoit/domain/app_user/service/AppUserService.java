package com.moyeoit.domain.app_user.service;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.global.auth.extractor.OAuth2UserProfile;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    /**
     * AppUser ID 기반 AppUser 조회
     */
    public AppUserDto getAppUser(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        return AppUserDto.of(user);
    }

    /**
     * OAuth2 기반 AppUser 생성
     */
    public AppUserDto findOrCreateAppUserFromOAuth2(OAuth2UserProfile profile) {
        Optional<AppUser> findAppUser = appUserRepository.findByEmail(profile.getEmail());
        if (findAppUser.isPresent()) {
            return AppUserDto.of(findAppUser.get());
        }

        AppUser user = AppUser.builder()
                .name(profile.getName())
                .email(profile.getEmail())
                .provider(profile.getProvider())
                .active(false)
                .build();

        AppUser savedUser = appUserRepository.save(user);
        return AppUserDto.of(savedUser);
    }

}