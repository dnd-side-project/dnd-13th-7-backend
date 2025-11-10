package com.moyeoit.domain.review.domain.v2;

import com.moyeoit.domain.review.domain.enums.ReviewElementCategory;
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

    @Column(name = "generation")
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_element_category")
    private ReviewElementCategory category;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<ReviewAnswer> answers;

}