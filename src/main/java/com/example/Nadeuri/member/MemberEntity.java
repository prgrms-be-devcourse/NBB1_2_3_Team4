package com.example.Nadeuri.member;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.comment.CommentEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;


@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member") //테이블이름
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

    @Builder //빌더 추가
    public MemberEntity(String userId, String email, String password, String role, String birthDate, String name, String profileImage, String nickname) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthDate = birthDate;
        this.name = name;
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 테이블 이름을 맵핑
    private List<BoardEntity> boards;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 테이블 이름을 맵핑
    private List<CommentEntity> comments;

    public void changeEmail(String email) {this.email = email;}

    public void changePassword(String password) {this.password = password;}

    public void changeProfileImage(String profileImage) {this.profileImage = profileImage;} //오타 수정

    public void changeBirthDate(String birthDate) {this.birthDate = birthDate;} //오타 수정

    public void changeNickname(String nickname) {this.nickname = nickname;}

    public void changeName(String name) {this.name = name;}

    public void changeRole(String role) {this.role = role;}


}
