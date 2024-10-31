package com.example.Nadeuri.member.dto

import com.example.Nadeuri.member.Role


data class MemberDTO(
    val userId: String,
    val password: String,
    val name: String,
    val email: String,
    val role: Role,
    val nickname: String,
    val profileImage: String?,
    val birthDate: String
) {
    // JWT 문자열의 내용을 반환하는 메서드
    fun getPayload(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "name" to name,
            "email" to email,
            "role" to role
        )
    }
}