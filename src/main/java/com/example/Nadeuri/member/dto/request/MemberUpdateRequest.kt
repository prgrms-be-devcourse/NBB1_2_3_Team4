package com.example.Nadeuri.member.dto.request

import com.example.Nadeuri.member.Role

data class MemberUpdateRequest (
    val role: Role? = null,
    val password: String?,
    val name: String?,
    val email: String?,
    val nickname: String?,
    val profileImage: String? = null,
    val deleteProfileImage: Boolean = false // 프로필 이미지 삭제 요청 필드
)
