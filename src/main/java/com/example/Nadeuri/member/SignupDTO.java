package com.example.Nadeuri.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO {
    private String userId;
    private String password;
    private String name;
    private String email;
    private String nickname;
    private String profileImage;
    private String birthDate;

    public MemberEntity toEntity(String encodedPassword, String role) {

        return MemberEntity.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthDate(birthDate)
                .password(encodedPassword)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(role)
                .build();
    }
}
