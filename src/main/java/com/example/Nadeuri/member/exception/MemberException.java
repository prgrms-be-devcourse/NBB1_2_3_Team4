package com.example.Nadeuri.member.exception;

public enum MemberException {
    NOT_FOUND("존재하지 않는 회원입니다.", 404),
    DUPLICATE("이미 존재하는 회원입니다.", 409),
    INVALID("INVALID", 400),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 401),
    NOT_MATCHED_USER("사용자가 일치하지 않습니다.", 400);

    private MemberTaskException memberTaskException;

    MemberException(String message, int code) {
        memberTaskException = new MemberTaskException(message, code);
    }

    public MemberTaskException get() {
        return memberTaskException;
    }
}
