package com.moyeoit.context.review.service;

import com.moyeoit.context.review.infra.BlogReviewQueryRepository;
import com.moyeoit.context.review.presentation.request.BlogReviewSearchRequest;
import com.moyeoit.context.review.presentation.response.BlogReviewResponse;
import com.moyeoit.context.review.presentation.response.BlogReviewResponseV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogReviewService {

    private final BlogReviewQueryRepository blogReviewQueryRepository;

    @Transactional(readOnly = true)
    public Page<BlogReviewResponseV2> search(BlogReviewSearchRequest request, Pageable pageable) {
        return blogReviewQueryRepository.search(request, pageable);
    }

}
