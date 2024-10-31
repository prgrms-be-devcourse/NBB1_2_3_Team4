package com.example.Nadeuri.board

import com.example.Nadeuri.comment.entity.CommentEntity
import com.example.Nadeuri.member.MemberEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Table(name = "board")
@Entity
data class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", nullable = false)
    var id: Long? = null,

    // 멤버 테이블 참조 FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // optional = FK(참조키)에 널 허용하지않음
    @JoinColumn(name = "member_no") // 다른 테이블의 컬럼명
    var member: MemberEntity? = null, // 멤버 객체로 변경 필요

    @Column(name = "board_title", nullable = false)
    var boardTitle: String,

    @Column(name = "board_content", nullable = false)
    var boardContent: String,

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "like_count")
    var likeCount: Int = 0,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true) // 테이블 이름을 맵핑
    var comments: List<CommentEntity> = listOf() // 한 게시물의 여러 댓글을 보여줌
)
//빌더가 없는 이유 -> 코틀린은 주 생성자만으로 값을 수정할수 있고 인자에 값을 할당하지 않으면 기본값이 들어감
    {
        companion object {
            fun create(
                member: MemberEntity,
                boardTitle: String,
                boardContent: String,
                category: Category,
                imageUrl: String?
            ): BoardEntity {
                return BoardEntity(
                    member = member,
                    boardTitle = boardTitle,
                    boardContent = boardContent,
                    category = category,
                    imageUrl = imageUrl
                )
            }
        }

        fun update(
            member: MemberEntity,
            boardTitle: String,
            boardContent: String,
            category: Category,
            imageUrl: String?
        ) {
            this.member = member
            this.boardTitle = boardTitle
            this.boardContent = boardContent
            this.category = category
            this.imageUrl = imageUrl
        }

        fun recordDeletion(deletedAt: LocalDateTime) {
            this.deletedAt = deletedAt
        }

        fun increaseLikeCount() {
            this.likeCount++
        }

        fun decreaseLikeCount() {
            this.likeCount--
        }
    }