package com.example.Nadeuri.comment.mapper

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.comment.dto.response.CommentResponseDTO
import com.example.Nadeuri.comment.dto.request.CommentRequestDTO
import com.example.Nadeuri.comment.entity.CommentEntity
import com.example.Nadeuri.member.MemberEntity
import org.springframework.stereotype.Component

@Component
class CommentMapper {
    fun toDto(commentEntity: CommentEntity): CommentResponseDTO {
        val replyDTOs = commentEntity.replies.map { toDto(it) }

        // board가 null일 경우 처리
        val boardId = commentEntity.board?.id ?: throw IllegalArgumentException("Board cannot be null for comment ID: ${commentEntity.id}")

        return CommentResponseDTO(
            id = commentEntity.id,
            boardId = boardId,
            memberId = commentEntity.member.userId,
            content = commentEntity.content,
            parentCommentId = commentEntity.parentComment?.id,
            createdAt = commentEntity.createdAt,
            updatedAt = commentEntity.updatedAt,
            replies = replyDTOs
        )
    }

    fun toEntity(
        commentRequestDTO: CommentRequestDTO,
        board: BoardEntity,
        member: MemberEntity,
        parentComment: CommentEntity? = null
    ): CommentEntity {
        return CommentEntity(
            id = null,
            board = board,
            member = member,
            content = commentRequestDTO.content,
            parentComment = parentComment
        )
    }
}

/*
 Entity와 DTO 간 변환을 담당하는 메퍼 클래스
 */