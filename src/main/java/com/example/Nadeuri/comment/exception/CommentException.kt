package com.example.Nadeuri.comment.exception

enum class CommentException(val message: String, val code: Int) {
    NOT_FOUND("[ERROR] 존재하지 않는 댓글입니다.", 404),
    NOT_REGISTERED("[ERROR] 댓글 등록에 실패했습니다.", 400),
    NOT_MODIFIED("[ERROR] 댓글 수정에 실패했습니다", 400),
    NOT_REMOVED("[ERROR] 댓글 삭제에 실패했습니다", 400),
    NOT_MATCHED_USER("[ERROR] 사용자가 일치하지 않습니다.", 400);

    fun get(): CommentTaskException {
        return CommentTaskException(message, code)
    }
}
