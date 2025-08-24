package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.club.entity.Club;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private AppUser user;

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
    
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PremiumReviewDetail> premiumReviewDetails = new ArrayList<>();
}