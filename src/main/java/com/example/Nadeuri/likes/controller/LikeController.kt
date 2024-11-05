package com.example.Nadeuri.likes.controller

import com.example.Nadeuri.likes.common.ApiResponse
import com.example.Nadeuri.likes.controller.dto.response.LikeDeleteResponse
import com.example.Nadeuri.likes.entity.LikeEntity
import com.example.Nadeuri.likes.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v2")
@RestController
class LikeController(
    private val likeService: LikeService
) {

    /**
     * 좋아요 등록
     */
    @PostMapping("/{boardId}/likes")
    fun like(
        authentication: Authentication,
        @PathVariable("boardId") boardId: Long,
    ): ResponseEntity<ApiResponse<*>> {
        likeService.like(authentication.name, boardId)
        return ResponseEntity.ok(ApiResponse.success(null))
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/{boardId}/likes")
    fun unLike(
        authentication: Authentication,
        @PathVariable("boardId") boardId: Long,
    ): ResponseEntity<ApiResponse<*>> {
        val like: LikeEntity = likeService.unLike(authentication.name, boardId)
        val response: LikeDeleteResponse = LikeDeleteResponse.from(like);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}