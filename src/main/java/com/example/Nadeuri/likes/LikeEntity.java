package com.example.Nadeuri.likes;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "likes")
@Entity
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "board_no") // LikeEntity의 FK
    private BoardEntity board;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Builder
    public LikeEntity(
            final Long id,
            final MemberEntity member,
            final BoardEntity board,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.member = member;
        this.board = board;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static LikeEntity create(
            final MemberEntity member,
            final BoardEntity board
    ) {
        return LikeEntity.builder()
                .member(member)
                .board(board)
                .build();
    }
    public void recordDeletion(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
