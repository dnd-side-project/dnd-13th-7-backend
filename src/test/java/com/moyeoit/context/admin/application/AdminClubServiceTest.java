package com.moyeoit.context.admin.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moyeoit.context.admin.presentation.request.AdminClubCreateRequest;
import com.moyeoit.context.club.application.service.ClubManageService;
import com.moyeoit.context.club.domain.entity.Club;
import com.moyeoit.context.club.domain.repository.ClubActivityRepository;
import com.moyeoit.context.club.domain.repository.ClubPositionRepository;
import com.moyeoit.context.club.domain.repository.ClubRecruitmentPartRepository;
import com.moyeoit.context.club.domain.repository.ClubRecruitmentRepository;
import com.moyeoit.context.club.domain.repository.ClubRepository;
import com.moyeoit.context.club.domain.repository.ClubScheduleRepository;
import com.moyeoit.context.club.domain.repository.ClubSubscribeRepository;
import com.moyeoit.context.club.domain.repository.ProcessRepository;
import com.moyeoit.context.club.domain.repository.TargetRepository;
import com.moyeoit.context.club.presentation.request.ClubSaveRequest;
import com.moyeoit.context.file.controller.response.FileUploadResponse;
import com.moyeoit.context.file.service.S3Service;
import com.moyeoit.context.user.domain.repository.UserActivityRepository;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class AdminClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ClubManageService clubManageService;

    @Mock
    private S3Service s3Service;

    @Mock
    private ClubActivityRepository clubActivityRepository;

    @Mock
    private ClubRecruitmentRepository clubRecruitmentRepository;

    @Mock
    private ClubRecruitmentPartRepository clubRecruitmentPartRepository;

    @Mock
    private ProcessRepository processRepository;

    @Mock
    private ClubScheduleRepository clubScheduleRepository;

    @Mock
    private ClubSubscribeRepository clubSubscribeRepository;

    @Mock
    private ClubPositionRepository clubPositionRepository;

    @Mock
    private TargetRepository targetRepository;

    @Mock
    private UserActivityRepository userActivityRepository;

    @InjectMocks
    private AdminClubService adminClubService;

    @Test
    void createClubUploadsImageFileBeforeSaving() throws IOException {
        AdminClubCreateRequest request = new AdminClubCreateRequest();
        request.setName("DND");
        request.setBio("long bio");
        request.setEstablishmentYear(2014);
        request.setRecruiting(true);
        request.setImageUrl("https://old.example/image.png");
        request.setImageFile(new MockMultipartFile("imageFile", "club.png", "image/png", "data".getBytes()));

        when(s3Service.uploadAndGetPublicUrl(any()))
                .thenReturn(new FileUploadResponse("https://cdn.example/club.png", "club", "png", 4L));

        adminClubService.createClub(request);

        ArgumentCaptor<ClubSaveRequest> captor = ArgumentCaptor.forClass(ClubSaveRequest.class);
        verify(clubManageService).saveClubDetail(captor.capture());
        assertThat(captor.getValue().getImageUrl()).isEqualTo("https://cdn.example/club.png");
    }

    @Test
    void deleteClubRemovesClubDependentsThenClub() {
        Long clubId = 7L;
        Club club = mock(Club.class);

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        adminClubService.deleteClub(clubId);

        verify(clubActivityRepository).deleteAllByClubId(clubId);
        verify(clubScheduleRepository).deleteAllByClubId(clubId);
        verify(processRepository).deleteAllByClubId(clubId);
        verify(clubRecruitmentPartRepository).deleteAllByClubId(clubId);
        verify(clubRecruitmentRepository).deleteByClubId(clubId);
        verify(clubSubscribeRepository).deleteAllByClubId(clubId);
        verify(clubPositionRepository).deleteAllByClubId(clubId);
        verify(targetRepository).deleteAllByClubId(clubId);
        verify(userActivityRepository).deleteAllByClubId(clubId);
        verify(clubRepository).deleteById(clubId);
    }
}
