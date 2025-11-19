package com.moyeoit.domain.review.domain.model;

import com.moyeoit.domain.review.domain.enums.ReviewCategory;
import com.moyeoit.domain.review.domain.enums.ReviewResult;
import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_review")
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "title")
    private String title; // 한줄평

    @Column(name = "rate")
    private Double rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private ReviewResult result;

    @Column(name = "generation")
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_element_category")
    private ReviewCategory category;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "comment_count")
    private Long commentCount;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<ReviewAnswer> answers;

}