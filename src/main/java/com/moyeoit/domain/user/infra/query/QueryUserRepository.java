package com.moyeoit.domain.user.infra.query;

import com.moyeoit.domain.user.service.dto.UserWithJobResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryUserRepository {

    public UserWithJobResult findUserWithJob(Long userId) {

    }
}
