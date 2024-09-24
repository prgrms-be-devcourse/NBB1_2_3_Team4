package com.example.Nadeuri.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "board")
@Entity
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", nullable = false)
    private Long id;

    @Column(name = "member_no", nullable = false)
    private Long memberId; //멤버 객체로 변경 필요

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Builder
    public BoardEntity(
            final Long id,
            final Long memberId,
            final String boardTitle,
            final String boardContent,
            final Category category,
            final String imageUrl,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static BoardEntity create(
            final Long memberId,
            final String boardTitle,
            final String boardContent,
            final Category category,
            final String imageUrl
    ) {
        return BoardEntity.builder()
                .memberId(memberId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .category(category)
                .imageUrl(imageUrl)
                .build();
    }
}
