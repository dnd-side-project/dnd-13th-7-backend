package com.moyeoit.context.community.domain;

import com.moyeoit.context.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @org.hibernate.annotations.JdbcTypeCode(java.sql.Types.LONGVARCHAR)
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count")
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false, length = 20)
    private CommentStatus status = CommentStatus.ACTIVE;

    @Column(name = "reported_at")
    private LocalDateTime reportedAt;

    @Column(name = "report_memo", length = 255)
    private String reportMemo;

    @Column(name = "deleted_content", columnDefinition = "TEXT")
    private String deletedContent;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateContent(String content) {
        this.content = content;
    }

    public void commentDelete() {
        this.isDeleted = true;
        this.status = CommentStatus.DELETED;
        if (this.deletedContent == null) {
            this.deletedContent = this.content;
        }
        this.content = "삭제된 댓글입니다.";
    }

    public void restore() {
        this.isDeleted = false;
        this.status = CommentStatus.ACTIVE;
        if (this.deletedContent != null) {
            this.content = this.deletedContent;
        }
    }

    public void report(String memo) {
        this.status = CommentStatus.REPORTED;
        this.reportedAt = LocalDateTime.now();
        this.reportMemo = memo;
    }

    public void blind(String memo) {
        this.status = CommentStatus.BLINDED;
        this.reportedAt = LocalDateTime.now();
        this.reportMemo = memo;
        if (this.deletedContent == null) {
            this.deletedContent = this.content;
        }
        this.content = "블라인드 처리된 댓글입니다.";
    }

    public void unblind() {
        if (this.status == CommentStatus.BLINDED) {
            this.status = CommentStatus.ACTIVE;
            if (this.isDeleted != null && this.isDeleted) {
                return;
            }
            if (this.deletedContent != null) {
                this.content = this.deletedContent;
            }
        }
    }
}
