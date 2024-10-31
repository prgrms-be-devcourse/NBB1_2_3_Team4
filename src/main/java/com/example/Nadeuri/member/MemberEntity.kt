package com.example.Nadeuri.member

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.comment.entity.CommentEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern

@Entity
@Table(name = "member")
data class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberNo: Long? = null,

    var userId: String,
    var password: String,
    @field:Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$",
        message = "Email pattern should be valid"
    )
    var email: String,
    var birthDate: String,
    var name: String,
    var nickname: String,
    var profileImage: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var boards: MutableList<BoardEntity> = mutableListOf(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<CommentEntity> = mutableListOf()
) {
    // 변경 메소드들
    fun changeEmail(email: String) {
        this.email = email
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun changeProfileImage(profileImage: String) {
        this.profileImage = profileImage
    }

    fun changeBirthDate(birthDate: String) {
        this.birthDate = birthDate
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changeName(name: String) {
        this.name = name
    }

    fun changeRole(role: Role) {
        this.role = role
    }
}

