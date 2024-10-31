package com.example.Nadeuri.member.dto.request

import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.Role
import jakarta.validation.constraints.NotBlank

data class SignupDTO(
    @field:NotBlank
    val userId: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val nickname: String,

    @field:NotBlank
    val birthDate: String,

    val imageUrl: String? = null // 기본값을 null로 설정
) {
    fun toEntity(encodedPassword: String, role: Role, imageUrl: String): MemberEntity {
        return MemberEntity(
            userId = userId,
            name = name,
            email = email,
            birthDate = birthDate,
            password = encodedPassword,
            nickname = nickname,
            profileImage = imageUrl,
            role = role // 등록 시 자동으로 USER로 설정
        )
    }
}