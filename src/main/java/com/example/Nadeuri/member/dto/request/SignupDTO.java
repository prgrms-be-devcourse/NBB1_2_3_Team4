package com.example.Nadeuri.member.dto.request;

import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String imageUrl;
    private String birthDate;

    public MemberEntity toEntity(String encodedPassword, Role role, String imageUrl) {

        return MemberEntity.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .birthDate(birthDate)
                .password(encodedPassword)
                .nickname(nickname)
                .profileImage(imageUrl)
                .role(role) //등록시에 USER로 자동설정
                .build();
    }
}
