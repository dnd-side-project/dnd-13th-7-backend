package com.moyeoit.context.club.application.service;

import com.moyeoit.context.club.presentation.request.ClubActivitySaveRequest;
import com.moyeoit.context.club.presentation.request.ClubProcessSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubRecruitmentSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubScheduleSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubUpdateRequest;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.entity.ClubActivityMethod;
import com.moyeoit.context.club.domain.entity.ClubPlace;
import com.moyeoit.context.club.domain.entity.ClubProfile;
import com.moyeoit.context.club.domain.repository.ClubActivityRepository;
import com.moyeoit.context.club.domain.repository.ClubRecruitmentRepository;
import com.moyeoit.context.club.domain.repository.ClubRepository;
import com.moyeoit.context.club.domain.repository.ClubScheduleRepository;
import com.moyeoit.context.club.domain.repository.ProcessRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubManageService {

    private final ClubRepository clubRepository;
    private final ClubActivityRepository clubActivityRepository;
    private final ClubRecruitmentRepository clubRecruitmentRepository;
    private final ProcessRepository clubProcessRepository;
    private final ClubScheduleRepository clubScheduleRepository;

    public void saveClubDetail(ClubSaveRequest request){
        clubRepository.save(ClubSaveRequest.of(request));
    }

    public void updateClubDetail(ClubUpdateRequest request){
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));

        ClubProfile currentProfile = club.getClubProfile();
        ClubActivityMethod currentMethod = club.getClubActivityMethod();
        ClubPlace currentPlace = club.getClubPlace();

        Integer establishmentYear = request.getEstablishment() != null
                ? request.getEstablishment()
                : (currentProfile != null && currentProfile.establishment() != null
                    ? currentProfile.establishment().getYear()
                    : null);
        LocalDate establishment = establishmentYear != null ? LocalDate.of(establishmentYear, 1, 1) : null;

        ClubProfile updatedProfile = new ClubProfile(
                firstNonNull(request.getSlogan(), currentProfile != null ? currentProfile.slogan() : null),
                firstNonNull(request.getBio(), currentProfile != null ? currentProfile.bio() : null),
                establishment,
                firstNonNull(request.getTotalParticipant(), currentProfile != null ? currentProfile.totalParticipant() : null),
                firstNonNull(request.getOperation(), currentProfile != null ? currentProfile.operation() : null),
                firstNonNull(request.getImageUrl(), currentProfile != null ? currentProfile.imageUrl() : null)
        );

        ClubActivityMethod updatedMethod = new ClubActivityMethod(
                firstNonNull(request.getOnline(), currentMethod != null ? currentMethod.online() : null),
                firstNonNull(request.getOffline(), currentMethod != null ? currentMethod.offline() : null)
        );

        ClubPlace updatedPlace = new ClubPlace(
                firstNonNull(request.getLocation(), currentPlace != null ? currentPlace.location() : null),
                firstNonNull(request.getAddress(), currentPlace != null ? currentPlace.address() : null)
        );

        Boolean recruiting = request.getRecruiting() != null ? request.getRecruiting() : club.getRecruiting();
        String significant = firstNonNull(request.getSignificant(), club.getSignificant());
        String name = firstNonNull(request.getName(), club.getName());

        club.updateBasicInfo(name, updatedProfile, updatedMethod, updatedPlace, recruiting, significant);
    }

    public void saveClubActivity(ClubActivitySaveRequest request){
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        clubActivityRepository.save(ClubActivitySaveRequest.of(request,club));
    }

    public void saveClubRecruit(ClubRecruitmentSaveRequest request){
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        clubRecruitmentRepository.save(ClubRecruitmentSaveRequest.of(request,club));
    }

    public void saveProcess(ClubProcessSaveRequest request){
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        clubProcessRepository.save(ClubProcessSaveRequest.of(request,club));
    }

    public void saveClubSchedule(ClubScheduleSaveRequest request){
        Club club = clubRepository.findById(request.getClubId()).orElseThrow(()->new AppException(ClubErrorCode.NOT_FOUND));
        clubScheduleRepository.save(ClubScheduleSaveRequest.of(request,club));
    }

    private <T> T firstNonNull(T value, T fallback) {
        return value != null ? value : fallback;
    }
}
