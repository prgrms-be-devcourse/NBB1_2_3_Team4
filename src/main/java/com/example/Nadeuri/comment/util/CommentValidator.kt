package com.example.Nadeuri.comment.util

import com.example.Nadeuri.comment.dto.request.CommentRequestDTO
import org.springframework.stereotype.Component

@Component
class CommentValidator {
    // 댓글 유효성 검사
    fun validateCommentRequest(commentRequestDTO: CommentRequestDTO) {
        validateContent(commentRequestDTO.content)
    }

    // 댓글 내용 유효성 검사
    fun validateContent(content: String) {
        if (content.isBlank()) {
            throw IllegalArgumentException("댓글 내용은 비어 있을 수 없습니다.")
        }
        if (content.length > 500) {
            throw IllegalArgumentException("댓글 내용은 500자를 초과할 수 없습니다.")
        }
    }
}

/*
 댓글 유효성 검증을 담당하는 유틸리티 클래스
 */