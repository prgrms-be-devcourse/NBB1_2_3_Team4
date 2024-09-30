package com.example.Nadeuri.member.exception;

public enum MemberException {
    NOT_FOUND("NOT_FOUND", 404),
    DUPLICATE("DUPLICATED_MEMBER", 409),
    INVALID("INVALID", 400),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 401),
    NOT_MATCHED_USER("NOT_MATCHED_USER", 400);

    private MemberTaskException memberTaskException;

    MemberException(String message, int code) {
        memberTaskException = new MemberTaskException(message, code);
    }

    public MemberTaskException get() {
        return memberTaskException;
    }
}
