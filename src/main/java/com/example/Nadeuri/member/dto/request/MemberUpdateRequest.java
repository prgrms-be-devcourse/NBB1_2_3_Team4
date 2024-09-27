package com.example.Nadeuri.member.dto.request;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private String password;
    private String name;
    private String email;
    private String role;
    private String nickname;
    private String profileImage;

    private boolean deleteProfileImage; // 프로필 이미지 삭제 요청 필드

}
