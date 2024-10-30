package com.example.Nadeuri.comment

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.member.MemberEntity
import jakarta.persistence.*
import lombok.Builder
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Table(name = "comment")
@Entity
class CommentEntity @Builder constructor(
    @field:Column(name = "comment_no", nullable = false)
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    private val id: Long? = null, // id는 nullable로

    @field:JoinColumn(name = "board_no", nullable = false)
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    private val board: BoardEntity,

    @field:JoinColumn(name = "member_no", nullable = false)
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    private val member: MemberEntity,

    @field:Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private var content: String,

    @field:JoinColumn(name = "parent_comment_no")
    @field:ManyToOne(fetch = FetchType.LAZY)
    private val parentComment: CommentEntity? = null, // nullable 처리

    @field:OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val replies: MutableList<CommentEntity> = mutableListOf() // 기본값으로 빈 리스트
) {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null

    // content 업데이트 메서드
    fun updateContent(content: String) {
        this.content = content
    }
}