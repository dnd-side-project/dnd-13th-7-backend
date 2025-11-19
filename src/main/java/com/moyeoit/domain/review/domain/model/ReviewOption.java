package com.moyeoit.domain.review.domain.model;

import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_review_option")
public class ReviewOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_question_id")
    private ReviewQuestion question;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "sequence")
    private Integer sequence;

}