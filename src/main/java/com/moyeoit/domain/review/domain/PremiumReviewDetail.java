package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.user.domain.AppUser;
import jakarta.persistence.*;
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