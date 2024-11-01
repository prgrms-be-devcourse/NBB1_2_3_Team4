package com.example.Nadeuri.board.kotlin.controller.dto

import com.example.Nadeuri.board.BoardEntity

data class BoardDeleteResponse2(
    val boardId: Long,
) {
    companion object {
        fun from(board: BoardEntity): BoardDeleteResponse2 =
            BoardDeleteResponse2(
                boardId = board.id
            )
    }
}
