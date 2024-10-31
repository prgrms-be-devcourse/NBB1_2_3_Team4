package com.example.Nadeuri.comment.dto.response

import com.example.Nadeuri.comment.entity.CommentEntity
import java.time.LocalDateTime

// 응답용 DTO
data class CommentResponseDTO(
    val id: Long? = null,
    val boardId: Long,
    val memberId: String,
    val content: String,
    val parentCommentId: Long? = null, // Nullable로 설정
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val replies: List<CommentResponseDTO> = emptyList() // 기본값으로 빈 리스트 설정
) {
    companion object {
        fun fromEntity(commentEntity: CommentEntity): CommentResponseDTO {
            val replyDTOs = commentEntity.replies.map { fromEntity(it) }

            // board가 null일 경우 처리
            val boardId = commentEntity.board?.id ?: throw IllegalArgumentException("Board cannot be null for comment ID: ${commentEntity.id}")

            return CommentResponseDTO(
                id = commentEntity.id,
                boardId = boardId,
                memberId = commentEntity.member.userId,
                content = commentEntity.content,
                parentCommentId = commentEntity.parentComment?.id, // Nullable로 안전하게 처리
                createdAt = commentEntity.createdAt,
                updatedAt = commentEntity.updatedAt,
                replies = replyDTOs
            )
        }
    }
}

/*
 댓글 응답 DTO
 */