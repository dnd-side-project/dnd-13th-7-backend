package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.review.domain.enums.AnswerType;

import com.moyeoit.domain.user.domain.User;
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
@Table(name = "tb_premium_review_detail")
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
    private User appUser;

    @Lob
    private String value;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
}