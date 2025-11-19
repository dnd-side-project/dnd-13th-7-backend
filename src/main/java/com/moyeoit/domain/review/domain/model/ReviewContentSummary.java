package com.moyeoit.domain.review.domain.model;

import com.moyeoit.domain.review.infra.converter.ChoiceSummaryConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "tb_review_content_summary")
@Builder
public class ReviewContentSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_content_summray_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Convert(converter = ChoiceSummaryConverter.class)
    @Column(name = "choice_summary")
    private List<String> choiceSummary;

    @Column(name = "subjective_summary")
    private String subjectiveSummary;

}
