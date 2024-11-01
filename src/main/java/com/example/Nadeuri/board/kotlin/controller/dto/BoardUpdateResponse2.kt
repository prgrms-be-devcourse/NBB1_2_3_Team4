package com.example.Nadeuri.board.kotlin.controller.dto

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.board.kotlin.domain.Category2

data class BoardUpdateResponse2(
    val memberId: Long,
    val boardTitle: String,
    val boardContent: String,
    val category2: Category2,
) {
    companion object {
        fun from(board: BoardEntity) =
            BoardUpdateResponse2(
                memberId = board.member.memberNo,
                boardTitle = board.boardTitle,
                boardContent = board.boardContent,
                category2 = board.category2
            )
    }
}
