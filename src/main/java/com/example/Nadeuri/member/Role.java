package com.example.Nadeuri.member;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    USER,
    MODERATOR,  //게시판 중간 관리자
    ADMIN;

    // 계층 구조 정의 (ADMIN은 모든 권한, MODERATOR는 USER 권한 포함)
    public Set<Role> getAuthorities() {
        if(this == ADMIN) {
            return EnumSet.of(ADMIN, MODERATOR, USER);
        } else if(this == MODERATOR) {
            return EnumSet.of(MODERATOR, USER);
        } else {
            return EnumSet.of(USER);
        }
    }
}
