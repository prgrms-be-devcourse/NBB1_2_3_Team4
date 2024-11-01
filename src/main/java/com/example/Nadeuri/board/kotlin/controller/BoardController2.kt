package com.example.Nadeuri.board.kotlin.controller

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.board.kotlin.common.ApiResponse2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardCreateRequest2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardDeleteResponse2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardUpdateRequest2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardUpdateResponse2
import com.example.Nadeuri.board.kotlin.service.BoardService2
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/v2/boards")
@RestController
class BoardController2(
    private val boardService2: BoardService2
) {

    /**
     * 게시글 등록
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun register(
        @RequestPart("request") request: @Valid BoardCreateRequest2,
        @RequestPart("image") multipartFile: MultipartFile?
    ): ResponseEntity<ApiResponse2<*>> {
        boardService2.register(request, multipartFile)
        return ResponseEntity.ok(ApiResponse2.success(null))
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    fun update(
        authentication: Authentication,
        @PathVariable("id") boardId: Long,
        @RequestPart("request") request: @Valid BoardUpdateRequest2,
        @RequestPart("image") multipartFile: MultipartFile?,
    ): ResponseEntity<ApiResponse2<*>> {
        val board: BoardEntity = boardService2.update(
            authentication.name, boardId, request,
            multipartFile
        )
        val response: BoardUpdateResponse2 = BoardUpdateResponse2.from(board)
        return ResponseEntity.ok(ApiResponse2.success(response))
    }

    /**
     * 게시판 삭제
     */
    @DeleteMapping("/{id}")
    fun delete(
        authentication: Authentication,
        @PathVariable("id") boardId: Long,
    ): ResponseEntity<ApiResponse2<*>> {
        val board: BoardEntity = boardService2.delete(authentication.name, boardId)
        val response = BoardDeleteResponse2.from(board)
        return ResponseEntity.ok(ApiResponse2.success(response))
    }
}