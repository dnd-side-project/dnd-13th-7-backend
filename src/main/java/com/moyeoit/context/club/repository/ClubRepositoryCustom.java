package com.moyeoit.context.club.repository;

import com.moyeoit.context.club.controller.request.ClubPagingRequest;
import com.moyeoit.context.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositoryCustom {
    Page<Club> findClubByRequest(ClubPagingRequest request, Pageable pageable);
}
