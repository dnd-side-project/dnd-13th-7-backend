package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.review.domain.enums.AnswerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PremiumReviewDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_premium_id")
    private PremiumReview review;

    @OneToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Lob
    private String value;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
}