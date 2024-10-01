package com.example.Nadeuri.member;

import com.example.Nadeuri.board.BoardEntity;
import com.example.Nadeuri.comment.CommentEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.log4j.Log4j2;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

//    @Column(nullable = false)  //간단하게 테스트 해보실 분들을 위해 주석처리 해놓았습니다.
    private String userId;
//    @Column(nullable = false)
    private String password;
//    @Column(nullable = false)
    private String email;
//    @Column(nullable = false)
    private String birthDate;
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
    private String nickname;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder //빌더 추가
    public MemberEntity(String userId, String email, String password, Role role, String birthDate, String name, String profileImage, String nickname) {
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

    public void changeRole(Role role) {
        this.role = role;
    }


}
