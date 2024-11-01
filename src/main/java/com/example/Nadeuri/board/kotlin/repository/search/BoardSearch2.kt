// Translated from Java to Kotlin. Framework: Spring
package com.example.Nadeuri.board.kotlin.repository.search

import com.example.Nadeuri.board.kotlin.controller.dto.BoardReadResponse2
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardSearch2 {
    fun pageDTO(pageable: Pageable): Page<BoardReadResponse2>
    fun pageSearch(keyword: String, pageable: Pageable): Page<BoardReadResponse2>
    fun pageFindBoardIsNotNull(pageable: Pageable): Page<BoardReadResponse2> //삭제한 게시물 페이지
}