package com.example.Nadeuri.comment.service.impl

import com.example.Nadeuri.comment.dto.response.CommentResponseDTO
import com.example.Nadeuri.comment.mapper.CommentMapper
import com.example.Nadeuri.comment.repository.CommentRepository
import com.example.Nadeuri.comment.service.CommentQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentQueryServiceImpl(
    private val commentRepository: CommentRepository,
    private val commentMapper: CommentMapper
) : CommentQueryService {

    @Transactional(readOnly = true)
    override fun readBoardId(boardId: Long): List<CommentResponseDTO> {
        val parentComments = commentRepository.findByBoardId(boardId)
        return parentComments.map { commentMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    override fun readMemberId(userId: String): List<CommentResponseDTO> {
        val comments = commentRepository.findByMemberUserId(userId)
        return comments.map { commentMapper.toDto(it) }
    }
}

/*
 댓글 조회 관련 로직 구현
 */