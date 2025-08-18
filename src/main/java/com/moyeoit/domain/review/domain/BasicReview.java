package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.Job;
import com.moyeoit.domain.club.entity.Club;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class BasicReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Club club;

    private Integer cohort;

    @OneToOne
    private Job job;

    @ManyToOne
    private AppUser user;

    private Double rate;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    private ReviewCategory reviewCategory;
    
    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasicReviewDetail> basicReviewDetails = new ArrayList<>();

}