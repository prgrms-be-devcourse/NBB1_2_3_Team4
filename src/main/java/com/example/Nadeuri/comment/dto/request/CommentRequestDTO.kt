package com.example.Nadeuri.comment.dto.request

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.comment.entity.CommentEntity
import com.example.Nadeuri.member.MemberEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

// 요청용 DTO
data class CommentRequestDTO(
    @field:NotNull(message = "게시글 ID는 필수입니다.")
    val boardId: Long,

    @field:NotBlank(message = "회원 ID는 필수입니다.")
    val userId: String,

    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    val content: String,

    val parentCommentId: Long? = null // 부모 댓글 ID - 답글인 경우
) {
    // dto -> entity 변환
    fun toEntity(board: BoardEntity, member: MemberEntity, parentComment: CommentEntity? = null): CommentEntity {
        return CommentEntity(
            id = null,
            board = board,
            member = member,
            content = this.content,
            parentComment = parentComment
        )
    }
}

/*
 댓글 생성 요청 DTO
 */