package com.example.Nadeuri.board.kotlin.controller

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.board.kotlin.common.ApiResponse2
import com.example.Nadeuri.board.kotlin.controller.dto.*
import com.example.Nadeuri.board.kotlin.service.BoardService2
import com.example.Nadeuri.member.controller.MemberController
import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import kotlin.math.log

@RequestMapping("/v2/boards")
@RestController
class BoardController2(
    private val boardService2: BoardService2
) {
    companion object {
        private val log: Logger = LogManager.getLogger(BoardController2::class.java)
    }


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
     * 게시글 조회
     */
    @GetMapping("/{id}")
    fun read(@PathVariable("id") boardId: Long): ResponseEntity<ApiResponse2<*>> {
        log.info("read controller()")
        return ResponseEntity.ok(ApiResponse2.success(boardService2.read(boardId)))
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping
    fun page(@Valid boardPageRequestDTO: BoardPageRequestDTO2): ResponseEntity<ApiResponse2<*>> {
        return ResponseEntity.ok(ApiResponse2.success(boardService2.page(boardPageRequestDTO)))
    }

    /**
     * 게시글 전체 조회 (검색)
     */
    @GetMapping("/search/{keyword}")
    fun pageSearch(@PathVariable("keyword") searchKeyword: String,
                   @Valid boardPageRequestDTO: BoardPageRequestDTO2): ResponseEntity<ApiResponse2<*>> {
        return ResponseEntity.ok(ApiResponse2.success(boardService2.pageSearch(searchKeyword, boardPageRequestDTO)))
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