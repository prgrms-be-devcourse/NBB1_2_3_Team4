package com.example.Nadeuri.board.exception;

public enum BoardException {
    NOT_FOUND("Board NOT_FOUND", 404),
    NOT_REGISTERED("Board NOT_REGISTERED", 400),
    NOT_MODIFIED("Board NOT_MODIFIED", 400),
    NOT_REMOVED("Board NOT_REMOVED", 400),
    NOT_FETCHED("Board NOT_FETCHED", 400),
    NO_IMAGE("Board NO_IMAGE", 400),
    REGISTER_ERR("NO AUTHENTICATED_USER",403);

    private BoardTaskException boardTaskException;

    BoardException(String message,int code){
        boardTaskException = new BoardTaskException(message,code);
    }

    public BoardTaskException get(){
        return boardTaskException;
    }
}
