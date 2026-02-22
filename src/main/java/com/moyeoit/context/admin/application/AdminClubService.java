package com.moyeoit.context.admin.application;

import com.moyeoit.context.admin.presentation.request.AdminClubCreateRequest;
import com.moyeoit.context.admin.presentation.request.AdminClubUpdateRequest;
import com.moyeoit.context.club.application.service.ClubManageService;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.club.presentation.request.ClubUpdateRequest;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminClubService {

    private final ClubRepository clubRepository;
    private final ClubManageService clubManageService;

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
                request.getImageUrl(),
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
                request.getImageUrl()
        );
        clubManageService.saveClubDetail(saveRequest);
    }
}
