package com.moyeoit.domain.club.infra.querydsl;

import com.moyeoit.domain.club.entity.QClub;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryClubRepository {

    private final JPAQueryFactory queryFactory;

    public void aa() {

    }

}
