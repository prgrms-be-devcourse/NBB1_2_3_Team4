package com.example.Nadeuri.member.dto.request;

import com.example.Nadeuri.member.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {
    private Role role;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    private String profileImage;

    private boolean deleteProfileImage; // 프로필 이미지 삭제 요청 필드

}
