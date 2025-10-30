package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.club.entity.Club;
import com.moyeoit.domain.user.domain.AppUser;
import com.moyeoit.domain.user.domain.Job;
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

    private Integer likeCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasicReviewDetail> basicReviewDetails = new ArrayList<>();

}