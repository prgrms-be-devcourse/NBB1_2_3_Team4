package com.example.Nadeuri.member.admin

import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.Role
import com.example.Nadeuri.member.dto.MemberDTO
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
// 어드민 서비스
class AdminService(private val memberRepository: MemberRepository,private val modelMapper: ModelMapper) {

    // 멤버 전체 조회
    fun getAdminMemberAll(): List<MemberDTO> {
        val members: List<MemberEntity> = memberRepository.findAll()

        // MemberEntity를 MemberDTO로 매핑
        return members.map { memberEntity ->
            MemberDTO(
                userId = memberEntity.userId ?: "",
                password = memberEntity.password ?: "",
                name = memberEntity.name ?: "",
                email = memberEntity.email ?: "",
                role = memberEntity.role ?: Role.USER,
                nickname = memberEntity.nickname ?: "",
                profileImage = memberEntity.profileImage,
                birthDate = memberEntity.birthDate ?: ""
            )
            //보조 생성자 사용
        }
    }

    // 멤버 조회
    fun getAdminMember(userId: String): MemberDTO {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Account not found")
        //널 허용이 되었으므로 널일경우 예외를 처리하도록 변경
        return MemberDTO(member)
    }

    // 멤버 수정
    fun updateAdminMember(userId: String, memberEntity: MemberEntity): MemberDTO {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Account not found")

        member.changeEmail(memberEntity.email)
        member.changeNickname(memberEntity.nickname)
        member.changeRole(memberEntity.role)
        member.changeBirthDate(memberEntity.birthDate)
        memberEntity.profileImage?.let { member.changeProfileImage(it) } // 이미지 변경시에만 업데이트
        //let 확장 함수를 사용하여 프로필이미지가 널이 아닐경우 프로필 이미지를 (it) 변경
        return MemberDTO(member)
        //피드백 반영하여 modelMapper로 일관
    }

    // 멤버 삭제
    fun deleteAdminMember(userId: String) {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Account not found")

        memberRepository.delete(member)
    }
}