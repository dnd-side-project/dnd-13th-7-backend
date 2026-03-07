package com.moyeoit.context.admin.application;

import com.moyeoit.context.admin.presentation.request.AdminClubCreateRequest;
import com.moyeoit.context.admin.presentation.request.AdminClubUpdateRequest;
import com.moyeoit.context.club.application.service.ClubManageService;
import com.moyeoit.context.club.domain.repository.ClubActivityRepository;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubUpdateRequest;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.repository.ClubPositionRepository;
import com.moyeoit.context.club.domain.repository.ClubRecruitmentRepository;
import com.moyeoit.context.club.domain.repository.ClubRecruitmentPartRepository;
import com.moyeoit.context.club.domain.repository.ClubRepository;
import com.moyeoit.context.club.domain.repository.ClubScheduleRepository;
import com.moyeoit.context.club.domain.repository.ClubSubscribeRepository;
import com.moyeoit.context.club.domain.repository.ProcessRepository;
import com.moyeoit.context.club.domain.repository.TargetRepository;
import com.moyeoit.context.file.service.S3Service;
import com.moyeoit.context.user.domain.repository.UserActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminClubService {

    private final ClubRepository clubRepository;
    private final ClubManageService clubManageService;
    private final S3Service s3Service;
    private final ClubActivityRepository clubActivityRepository;
    private final ClubRecruitmentRepository clubRecruitmentRepository;
    private final ClubRecruitmentPartRepository clubRecruitmentPartRepository;
    private final ProcessRepository processRepository;
    private final ClubScheduleRepository clubScheduleRepository;
    private final ClubSubscribeRepository clubSubscribeRepository;
    private final ClubPositionRepository clubPositionRepository;
    private final TargetRepository targetRepository;
    private final UserActivityRepository userActivityRepository;

    @Transactional(readOnly = true)
    public Page<Club> getClubs(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return clubRepository.findByNameContaining(keyword, pageable);
        }
        return clubRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club not found. id=" + clubId));
    }

    @Transactional
    public void updateClub(Long clubId, AdminClubUpdateRequest request) {
        ClubUpdateRequest updateRequest = new ClubUpdateRequest(
                clubId,
                request.getName(),
                request.getSlogan(),
                request.getBio(),
                request.getEstablishmentYear(),
                request.getTotalParticipant(),
                request.getOperation(),
                request.getOffline(),
                request.getOnline(),
                request.getLocation(),
                request.getAddress(),
                request.getRecruiting(),
                resolveImageUrl(request.getImageUrl(), request.getImageFile()),
                request.getSignificant()
        );
        clubManageService.updateClubDetail(updateRequest);
    }

    @Transactional
    public void createClub(AdminClubCreateRequest request) {
        ClubSaveRequest saveRequest = new ClubSaveRequest(
                request.getName(),
                request.getSlogan(),
                request.getBio(),
                request.getEstablishmentYear(),
                request.getTotalParticipant(),
                request.getOperation(),
                request.getOffline(),
                request.getOnline(),
                request.getLocation(),
                request.getAddress(),
                request.getRecruiting(),
                resolveImageUrl(request.getImageUrl(), request.getImageFile())
        );
        clubManageService.saveClubDetail(saveRequest);
    }

    @Transactional
    public void deleteClub(Long clubId) {
        getClub(clubId);

        clubActivityRepository.deleteAllByClubId(clubId);
        clubScheduleRepository.deleteAllByClubId(clubId);
        processRepository.deleteAllByClubId(clubId);
        clubRecruitmentPartRepository.deleteAllByClubId(clubId);
        clubRecruitmentRepository.deleteByClubId(clubId);
        clubSubscribeRepository.deleteAllByClubId(clubId);
        clubPositionRepository.deleteAllByClubId(clubId);
        targetRepository.deleteAllByClubId(clubId);
        userActivityRepository.deleteAllByClubId(clubId);
        clubRepository.deleteById(clubId);
    }

    private String resolveImageUrl(String imageUrl, MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return imageUrl;
        }

        try {
            return s3Service.uploadAndGetPublicUrl(imageFile).getFileUrl();
        } catch (IOException e) {
            throw new IllegalStateException("클럽 이미지 업로드에 실패했습니다.", e);
        }
    }
}
