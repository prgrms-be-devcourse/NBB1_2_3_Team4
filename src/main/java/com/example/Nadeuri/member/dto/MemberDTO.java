package com.example.Nadeuri.member.dto;


import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String userId;
    private String password;
    private String name;
    private String email;
    private Role role;
    private String nickname;
    private String profileImage;
    private String birthDate;


    public MemberDTO(MemberEntity member) {
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
        this.birthDate = member.getBirthDate();
    }

//    public MemberEntity toEntity(String encodedPassword, String role) {
//
//        return MemberEntity.builder()
//                .userId(userId)
//                .name(name)
//                .password(encodedPassword)
//                .nickname(nickname)
//                .profileImage(profileImage)
//                .role(role)
//                .build();
//    }

    //JWT 문자열의 내용 반환
    public Map<String, Object> getPayload() {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("userId", userId);
        payloadMap.put("name", name);
        payloadMap.put("email", email);
        payloadMap.put("role", role);
        return payloadMap;
    }

}
