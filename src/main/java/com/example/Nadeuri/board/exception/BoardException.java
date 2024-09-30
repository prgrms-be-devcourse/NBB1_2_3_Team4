package com.example.Nadeuri.board.exception;

import com.example.Nadeuri.member.exception.MemberTaskException;

public enum BoardException {
    NOT_FOUND("[ERROR] 존재하지 않는 게시물입니다.", 404),
    NOT_REGISTERED("[ERROR] 게시글 등록에 실패했습니다.", 400),
    NOT_MODIFIED("[ERROR] 게시글 수정에 실패했습니다", 400),
    NOT_REMOVED("[ERROR] 게시글 삭제에 실패했습니다", 400),
    NOT_MATCHED_USER("[ERROR] 사용자가 일치하지 않습니다.", 400);

    private BoardTaskException boardTaskException;

    BoardException(String message, int code) {
        boardTaskException = new BoardTaskException(message, code);
    }

    public BoardTaskException get() {
        return boardTaskException;
    }
}
