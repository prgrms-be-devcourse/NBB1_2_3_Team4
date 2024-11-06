package com.example.Nadeuri.comment.entity

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.member.MemberEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "comment")
data class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_no", nullable = false)
    val board: BoardEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    val member: MemberEntity,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no")
    var parentComment: CommentEntity? = null,

    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL], orphanRemoval = true)
    var replies: MutableList<CommentEntity> = mutableListOf(),

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
) {
    // content 업데이트 메서드
    fun updateContent(content: String) {
        this.content = content
    }

    // 대댓글 추가
    fun addReply(reply: CommentEntity) {
        replies.add(reply)
        reply.parentComment = this // 부모 댓글 설정
    }

    // 대댓글 제거
    fun removeReply(reply: CommentEntity) {
        replies.remove(reply)
        reply.parentComment = null // 부모 댓글 설정 해제
    }
}

/*
 댓글 엔티티 정의
 */