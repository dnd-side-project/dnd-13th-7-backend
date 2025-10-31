package com.moyeoit.domain.review.domain;

import com.moyeoit.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_premium_review_comment")
public class PremiumReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "premium_review_id")
    private PremiumReview premiumReview;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private User appUser;

    @Column(name = "content")
    private String content;

    @Column(name = "parent_comment_id", nullable = true)
    private Long parentCommentId;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


}
