package com.moyeoit.domain.club.repository;

import com.moyeoit.domain.club.controller.request.ClubPagingRequest;
import com.moyeoit.domain.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositoryCustom {
    Page<Club> findClubByRequest(ClubPagingRequest request, Pageable pageable);
}
