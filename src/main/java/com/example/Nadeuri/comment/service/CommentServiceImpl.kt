package com.example.Nadeuri.comment.service.impl

import com.example.Nadeuri.comment.dto.request.CommentRequestDTO
import com.example.Nadeuri.comment.dto.response.CommentResponseDTO
import com.example.Nadeuri.comment.entity.CommentEntity
import com.example.Nadeuri.comment.exception.CommentException
import com.example.Nadeuri.comment.repository.CommentRepository
import com.example.Nadeuri.comment.mapper.CommentMapper
import com.example.Nadeuri.comment.service.CommentService
import com.example.Nadeuri.comment.util.CommentValidator
import com.example.Nadeuri.board.kotlin.repository.BoardRepository2
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.exception.MemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("commentService")

class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository2,
    private val memberRepository: MemberRepository,
    private val commentMapper: CommentMapper,
    private val commentValidator: CommentValidator
) : CommentService {

    @Transactional
    override fun createComment(commentRequestDTO: CommentRequestDTO): CommentResponseDTO {
        commentValidator.validateCommentRequest(commentRequestDTO)

        val board = boardRepository.findById(commentRequestDTO.boardId)
            .orElseThrow { CommentException.NOT_FOUND.get() }

        val member = memberRepository.findByUserId(commentRequestDTO.userId)
            ?: throw MemberException.NOT_FOUND.get()

        val parentComment = commentRequestDTO.parentCommentId?.let {
            commentRepository.findById(it).orElseThrow { CommentException.NOT_FOUND.get() }
        }

        val comment = commentMapper.toEntity(commentRequestDTO, board, member, parentComment)
        return commentMapper.toDto(commentRepository.save(comment))
    }

    @Transactional
    override fun updateComment(commentId: Long, content: String, userId: String): CommentResponseDTO {
        val comment = check(commentId, userId)
        commentValidator.validateContent(content)
        comment.updateContent(content)
        return commentMapper.toDto(comment)
    }

    @Transactional
    override fun deleteComment(commentId: Long, userId: String) {
        val comment = check(commentId, userId)
        commentRepository.delete(comment)
    }

    override fun checkOwner(commentId: Long, userId: String): Boolean {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CommentException.NOT_FOUND.get() }
        return comment.member.userId == userId
    }

    private fun check(commentId: Long, userId: String): CommentEntity {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { CommentException.NOT_FOUND.get() }

        if (comment.member.userId != userId) {
            throw CommentException.NOT_MATCHED_USER.get()
        }

        return comment
    }
}

/*
 댓글 생성, 수정, 삭제 관련 비즈니스 로직 구현
 */