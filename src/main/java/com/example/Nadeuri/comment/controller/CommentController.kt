package com.example.Nadeuri.comment.controller

import com.example.Nadeuri.comment.dto.response.CommentResponseDTO
import com.example.Nadeuri.comment.dto.request.CommentRequestDTO
import com.example.Nadeuri.comment.service.CommentService
import com.example.Nadeuri.member.security.auth.CustomUserPrincipal
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/comments")
class CommentController(private val commentService: CommentService) {

    // 댓글 생성 - 부모 댓글이 있을 경우 해당 정보도 함께 처리
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createComment(
        @Valid @RequestBody commentRequestDTO: CommentRequestDTO,
        @AuthenticationPrincipal user: CustomUserPrincipal
    ): ResponseEntity<CommentResponseDTO> {
        val userId = user.name

        // 새로운 댓글 생성 요청 처리
        val createdComment = commentService.createComment(commentRequestDTO.copy(userId = userId))
        return ResponseEntity.ok(createdComment)
    }

    // 댓글 수정 - 자신의 댓글이거나 ADMIN 경우만 가능
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or #user.name == authentication.name")
    fun updateComment(
        @PathVariable commentId: Long,
        @Valid @RequestBody commentRequestDTO: CommentRequestDTO,
        @AuthenticationPrincipal user: CustomUserPrincipal
    ): ResponseEntity<CommentResponseDTO> {
        val userId = user.name

        // 업데이트된 댓글 요청 처리
        val updatedComment = commentService.updateComment(commentId, commentRequestDTO.content, userId)
        return ResponseEntity.ok(updatedComment)
    }

    // 댓글 삭제 - 자신의 댓글이거나 ADMIN 경우만 가능
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.checkOwner(#commentId, authentication.name)")
    fun deleteComment(
        @PathVariable commentId: Long,
        @AuthenticationPrincipal user: CustomUserPrincipal
    ): ResponseEntity<Void> {
        val userId = user.name
        commentService.deleteComment(commentId, userId)
        return ResponseEntity.noContent().build()
    }
}


/*
 댓글 생성, 수정, 삭제 등 비즈니스 로직을 처리하는 컨트롤러

 ㄷ
 */