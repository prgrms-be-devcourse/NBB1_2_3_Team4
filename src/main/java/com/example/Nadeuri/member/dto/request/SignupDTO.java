package com.example.Nadeuri.member.dto.request;

import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String birthDate;

    private String imageUrl;



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
