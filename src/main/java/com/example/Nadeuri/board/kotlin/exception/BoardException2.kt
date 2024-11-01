package com.example.Nadeuri.board.kotlin.exception

enum class BoardException2(
    val message: String,
    val code: Int
) {
    NOT_FOUND("[ERROR] 존재하지 않는 게시물입니다.", 404),
    NOT_REGISTERED("[ERROR] 게시글 등록에 실패했습니다.", 400),
    NOT_MODIFIED("[ERROR] 게시글 수정에 실패했습니다", 400),
    NOT_REMOVED("[ERROR] 게시글 삭제에 실패했습니다", 400),
    NOT_MATCHED_USER("[ERROR] 사용자가 일치하지 않습니다.", 400);

    private val boardTaskException2 = BoardTaskException2(message, code)

    fun get(): BoardTaskException2 {
        return boardTaskException2
    }
}