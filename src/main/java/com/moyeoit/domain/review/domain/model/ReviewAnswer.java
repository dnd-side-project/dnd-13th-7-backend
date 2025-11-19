package com.moyeoit.domain.review.domain.model;

import com.moyeoit.domain.review.domain.enums.AnswerType;
import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_review_answer")
@Builder
public class ReviewAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "review_question_id")
    private Long reviewQuestionId;

    @Column(name = "value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private AnswerType valueType;

    @Column(name = "numeric_value")
    private Double numericValue;

    @Column(name = "sequence")
    private Integer sequence;

}