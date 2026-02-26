package com.moyeoit.context.deprecated.bookmark.infra.entity;

import com.moyeoit.context.deprecated.bookmark.domain.model.Bookmark;
import com.moyeoit.context.deprecated.bookmark.presentation.request.BookmarkType;
import com.moyeoit.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_bookmark")
public class BookmarkEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BookmarkType type;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public static BookmarkEntity from(Bookmark bookmark) {
        return BookmarkEntity.builder()
                .id(bookmark.getId())
                .userId(bookmark.getUserId())
                .targetId(bookmark.getTargetId())
                .type(bookmark.getType())
                .isActive(bookmark.getIsActive())
                .build();
    }

    public Bookmark toModel() {
        return Bookmark.builder()
                .id(this.id)
                .userId(this.userId)
                .targetId(this.targetId)
                .type(this.type)
                .isActive(this.isActive)
                .build();
    }
}
