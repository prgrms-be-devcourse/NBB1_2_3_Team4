package com.example.Nadeuri.likes.exception;

public enum LikeException {
    ALREADY_LIKED_BOARD("[ERROR] 좋아요는 한 번만 가능합니다.", 409);

    private LikeTaskException likeTaskException;

    LikeException(String message, int code) {
        likeTaskException = new LikeTaskException(message, code);
    }

    public LikeTaskException get() {
        return likeTaskException;
    }
}
