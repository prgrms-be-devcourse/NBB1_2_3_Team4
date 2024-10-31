package com.example.Nadeuri.comment.service

import com.example.Nadeuri.comment.dto.response.CommentResponseDTO

interface CommentQueryService {
    fun readBoardId(boardId: Long): List<CommentResponseDTO>
    fun readMemberId(userId: String): List<CommentResponseDTO>
}

/*
 댓글 조회 관련 인터페이스 정의
 */