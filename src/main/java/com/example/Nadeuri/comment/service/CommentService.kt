package com.example.Nadeuri.comment.service

import com.example.Nadeuri.comment.dto.request.CommentRequestDTO
import com.example.Nadeuri.comment.dto.response.CommentResponseDTO

interface CommentService {
    fun createComment(commentRequestDTO: CommentRequestDTO): CommentResponseDTO
    fun updateComment(commentId: Long, content: String, userId: String): CommentResponseDTO
    fun deleteComment(commentId: Long, userId: String)
    fun checkOwner(commentId: Long, userId: String): Boolean
}

/*
 댓글 생성, 수정, 삭제 관련 인터페이스 정의
 */