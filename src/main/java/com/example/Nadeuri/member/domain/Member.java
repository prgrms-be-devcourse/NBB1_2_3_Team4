package com.example.Nadeuri.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_member")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;

    private String birthDate;
    private String name;
    private String profileImage;
    private String nickname;


    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeRole(String role) {
        this.role = role;
    }

    public void changeProfileImage(String birthDate) {
        this.birthDate = birthDate;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
