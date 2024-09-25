package com.example.Nadeuri.member;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member")
public class MemberEntity {
    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    private String userId;
    private String email;
    private String password;
    private String role;

    private String birthDate;
    private String name;
    private String profileImage;
    private String nickname;


    public void changeEmail(String email) {this.email = email;}

    public void changePassword(String password) {this.password = password;}

    public void changeProfileImage(String birthDate) {this.birthDate = birthDate;}

    public void changeNickname(String nickname) {this.nickname = nickname;}

    public void changeRole(String role) {this.role = role;}


}
