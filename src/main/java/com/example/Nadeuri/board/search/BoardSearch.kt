// Translated from Java to Kotlin. Framework: Spring
package com.example.Nadeuri.board.search

import com.example.Nadeuri.board.dto.BoardDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardSearch {
    fun pageDTO(pageable: Pageable): Page<BoardDTO>
    fun pageSearch(keyword: String, pageable: Pageable): Page<BoardDTO>
    fun pageFindBoardIsNotNull(pageable: Pageable): Page<BoardDTO> //삭제한 게시물 페이지
}