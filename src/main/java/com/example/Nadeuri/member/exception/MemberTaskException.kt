package com.example.Nadeuri.member.exception

class MemberTaskException(
    override val message: String,
    val code: Int
) : RuntimeException(message)