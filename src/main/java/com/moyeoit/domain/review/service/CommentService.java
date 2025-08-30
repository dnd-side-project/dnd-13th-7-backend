package com.moyeoit.domain.review.service;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.repository.AppUserRepository;
import com.moyeoit.domain.app_user.service.dto.AppUserDto;
import com.moyeoit.domain.review.controller.request.CommentCreateRequest;
import com.moyeoit.domain.review.controller.response.CommentResponse;
import com.moyeoit.domain.review.domain.PremiumReview;
import com.moyeoit.domain.review.domain.PremiumReviewComment;
import com.moyeoit.domain.review.repository.PremiumReviewCommentRepository;
import com.moyeoit.domain.review.repository.PremiumReviewRepository;
import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.code.ReviewErrorCode;
import com.moyeoit.global.exception.code.UserErrorCode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PremiumReviewRepository premiumReviewRepository;
    private final AppUserRepository appUserRepository;
    private final PremiumReviewCommentRepository premiumReviewCommentRepository;

    public List<CommentResponse> getCommentOfPremiumReview(Long premiumReviewId) {
        List<PremiumReviewComment> all = premiumReviewCommentRepository.findComments(premiumReviewId);

        if (all.isEmpty()) {
            return List.of();
        }

        List<PremiumReviewComment> roots = all.stream()
                .filter(c -> c.getParentCommentId() == null)
                .toList();

        Map<Long, List<PremiumReviewComment>> childrenByParent = all.stream()
                .filter(c -> c.getParentCommentId() != null)
                .collect(Collectors.groupingBy(
                        PremiumReviewComment::getParentCommentId,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            list.sort(Comparator
                                    .comparing(PremiumReviewComment::getCreateDate)
                                    .thenComparing(PremiumReviewComment::getId));
                            return list;
                        }))
                );

        return roots.stream()
                .map(r -> mappingRoot(r, childrenByParent.getOrDefault(r.getId(), List.of())))
                .toList();
    }

    @Transactional
    public CommentResponse createCommentOfPremiumReview(Long premiumReviewId, Long appUserId,
                                                        CommentCreateRequest request) {

        PremiumReview premiumReview = premiumReviewRepository.findById(premiumReviewId)
                .orElseThrow(() -> new AppException(ReviewErrorCode.NOT_FOUND));

        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new AppException(UserErrorCode.NOT_FOUND));

        PremiumReviewComment comment = PremiumReviewComment.builder()
                .premiumReview(premiumReview)
                .appUser(appUser)
                .content(request.getContent())
                .parentCommentId(request.getParentCommentId())
                .build();

        PremiumReviewComment savedComment = premiumReviewCommentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    private CommentResponse mappingRoot(PremiumReviewComment root,
                                        List<PremiumReviewComment> children) {

        List<CommentResponse> childs = children.stream()
                .map(child -> new CommentResponse(child.getId(),
                        child.getPremiumReview().getId(),
                        child.getContent(),
                        AppUserDto.of(child.getAppUser()),
                        List.of()))
                .toList();

        CommentResponse rootDto = new CommentResponse(root.getId(),
                root.getPremiumReview().getId(),
                root.getContent(),
                AppUserDto.of(root.getAppUser()),
                childs);

        return rootDto;
    }

}
