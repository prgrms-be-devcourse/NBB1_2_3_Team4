package com.example.Nadeuri.likes.entity

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.member.MemberEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Table(name = "likes")
@Entity
class LikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no", nullable = false)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "member_no")
    val member: MemberEntity,

    @ManyToOne
    @JoinColumn(name = "board_no") // LikeEntity의 FK
    val board: BoardEntity,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
) {

    companion object {
        fun create(
            member: MemberEntity,
            board: BoardEntity
        ): LikeEntity {
            return LikeEntity(
                member = member,
                board = board
            )
        }
    }

    fun setLikeDeleted(isDeleted: Boolean) {
        this.isDeleted = isDeleted
    }
}

