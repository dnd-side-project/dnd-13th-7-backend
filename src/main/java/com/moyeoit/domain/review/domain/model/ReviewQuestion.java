package com.moyeoit.domain.review.domain.model;

import com.moyeoit.domain.review.domain.enums.QuestionType;
import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_review_question")
public class ReviewQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_question_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuestionType type;

    @Column(name = "limit_value")
    private Long limitValue;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<ReviewOption> options;

}