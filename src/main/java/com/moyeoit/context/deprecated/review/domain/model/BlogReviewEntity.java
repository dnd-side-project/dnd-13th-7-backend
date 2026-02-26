package com.moyeoit.context.deprecated.review.domain.model;

import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_blog_review")
public class BlogReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_review_id")
    private Long id;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "title")
    private String title;

    @Column(name = "blog_name")
    private String blogName;

    @Column(name = "generation")
    private Integer generation;

    @Column(name = "blog_url")
    private String blogUrl;

    @Column(name = "image_url")
    private String imageUrl;

}