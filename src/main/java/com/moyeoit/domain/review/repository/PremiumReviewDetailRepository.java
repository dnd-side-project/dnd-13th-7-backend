package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.PremiumReviewDetail;
import com.moyeoit.domain.review.dto.PremiumReviewDetailDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumReviewDetailRepository extends JpaRepository<PremiumReviewDetail, Long> {
    Optional<PremiumReviewDetail> findFirstByIdOrderByIdAsc(Long reviewPremiumId);

    List<PremiumReviewDetail> findAllByReviewIdIn(List<Long> reviewIds);
}
