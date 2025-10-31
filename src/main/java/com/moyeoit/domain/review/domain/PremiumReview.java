package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.user.domain.Job;
import com.moyeoit.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_premium_review")
public class PremiumReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    private Integer cohort;

    @OneToOne(fetch = FetchType.LAZY)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String imageUrl;

    private String title;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    private ReviewCategory reviewCategory;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


    private Integer likeCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PremiumReviewDetail> premiumReviewDetails = new ArrayList<>();
}