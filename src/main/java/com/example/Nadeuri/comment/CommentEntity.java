package com.example.Nadeuri.comment;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
@Entity
public class CommentEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_no", nullable = false)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private MemberEntity member; //멤버 엔티티로 변경

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public CommentEntity(Long id, BoardEntity board, MemberEntity member, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.board = board;
        this.member = member;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}