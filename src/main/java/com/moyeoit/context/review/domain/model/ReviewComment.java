package com.moyeoit.context.review.domain.model;

import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_review_comment")
@Builder
public class ReviewComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ReviewComment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<ReviewComment> children = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.content = "DELETED_REVIEW_CONTENT";
        this.deleted = true;
        this.deletedDate = LocalDateTime.now();
    }

    public boolean isAuthor(Long userId) {
        return this.userId.equals(userId);
    }

}