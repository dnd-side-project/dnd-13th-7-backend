package com.moyeoit.domain.club.service;

import com.moyeoit.domain.club.controller.request.ClubActivitySaveRequest;
import com.moyeoit.domain.club.controller.request.ClubProcessSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubRecruitmentSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubSaveRequest;
import com.moyeoit.domain.club.controller.request.ClubScheduleSaveRequest;
import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.club.repository.ClubActivityRepository;
import com.moyeoit.domain.club.repository.ClubRecruitmentRepository;
import com.moyeoit.domain.club.repository.ClubRepository;
import com.moyeoit.domain.club.repository.ClubScheduleRepository;
import com.moyeoit.domain.club.repository.ProcessRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ClubErrorCode;
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
}