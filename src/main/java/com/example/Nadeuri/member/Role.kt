package com.example.Nadeuri.member

import java.util.*

enum class Role {
    USER,
    MODERATOR,  // 게시판 중간 관리자
    ADMIN;

    // 계층 구조 정의 (ADMIN은 모든 권한, MODERATOR는 USER 권한 포함)
    fun getAuthorities(): Set<Role> {
        return when (this) {
            ADMIN -> EnumSet.of(ADMIN, MODERATOR, USER)
            MODERATOR -> EnumSet.of(MODERATOR, USER)
            USER -> EnumSet.of(USER)
        }
    }
}
