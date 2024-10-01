package com.example.Nadeuri.likes.exception;

public enum LikeException {
    ALREADY_LIKED_BOARD("[ERROR] 좋아요는 한 번만 가능합니다.", 409),
    LIKE_NOT_EXIST("[ERROR] 좋아요가 존재하지 않습니다. 먼저 좋아요를 눌러주세요.", 404);

    private LikeTaskException likeTaskException;

    LikeException(String message, int code) {
        likeTaskException = new LikeTaskException(message, code);
    }

    public LikeTaskException get() {
        return likeTaskException;
    }
}
