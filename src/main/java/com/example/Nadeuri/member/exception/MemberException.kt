package com.example.Nadeuri.member.exception

enum class MemberException(val message: String, val code: Int) {
    NOT_FOUND("NOT_FOUND", 404),
    DUPLICATE("DUPLICATED_MEMBER", 409),
    INVALID("INVALID", 400),
    BAD_CREDENTIALS("BAD_CREDENTIALS", 401),
    NOT_MATCHED_USER("NOT_MATCHED_USER", 400);

    private val memberTaskException = MemberTaskException(message, code)

    fun get(): MemberTaskException = memberTaskException
}
