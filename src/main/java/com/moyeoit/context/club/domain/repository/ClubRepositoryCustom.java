package com.moyeoit.context.club.domain.repository;

import com.moyeoit.context.club.presentation.request.ClubPagingRequest;
import com.moyeoit.context.club.domain.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositoryCustom {
    Page<Club> findClubByRequest(ClubPagingRequest request, Pageable pageable, Long userId);
}
