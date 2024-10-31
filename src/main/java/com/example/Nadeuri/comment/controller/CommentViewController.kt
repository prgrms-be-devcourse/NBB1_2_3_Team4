package com.example.Nadeuri.comment.controller

import com.example.Nadeuri.comment.dto.response.CommentResponseDTO
import com.example.Nadeuri.comment.service.CommentService
import com.example.Nadeuri.comment.service.impl.CommentQueryServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 조회 관련 기능을 위한 컨트롤러
@RestController
@RequestMapping("/v1/comments/view")
class CommentViewController(
    private val commentService: CommentService,
    private val commentQueryServiceImpl: CommentQueryServiceImpl
) {

    // 게시글에 달린 모든 댓글 조회 - 계층형 구조로 조회
    @GetMapping("/board/{boardId}")
    fun readBoard(@PathVariable boardId: Long): ResponseEntity<List<CommentResponseDTO>> {
        val comments = commentQueryServiceImpl.readBoardId(boardId)
        return ResponseEntity.ok(comments)
    }

    // 사용자가 작성한 모든 댓글 조회 - 누구나 조회 가능
    @GetMapping("/member/{memberId}")
    fun readMember(@PathVariable memberId: String): ResponseEntity<List<CommentResponseDTO>> {
        val comments = commentQueryServiceImpl.readMemberId(memberId)
        return ResponseEntity.ok(comments)
    }
}

/*
 댓글 조회 로직을 처리하는 컨트롤러
 */