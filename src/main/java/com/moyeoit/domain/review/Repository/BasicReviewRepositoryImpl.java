package com.moyeoit.domain.review.Repository;

import static com.moyeoit.domain.review.domain.QBasicReview.basicReview;


import com.moyeoit.domain.review.controller.request.ReviewPagingRequest;
import com.moyeoit.domain.review.domain.BasicReview;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BasicReviewRepositoryImpl implements BasicReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BasicReview> findBasicReviewByRequest(ReviewPagingRequest request, Pageable pageable) {
        return null;
    }



    private OrderSpecifier<?> getOrderSpecifier(String sort){
        //todo: 날짜기준 최신순 추가, 좋아요 기준 인기순 추가예정
        return basicReview.id.desc();
    }

}
