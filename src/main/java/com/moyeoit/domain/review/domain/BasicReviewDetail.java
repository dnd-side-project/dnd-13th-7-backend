package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.domain.user.domain.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasicReviewDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_basic_id")
    private BasicReview review;

    @OneToOne
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Lob
    private String value;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

}
