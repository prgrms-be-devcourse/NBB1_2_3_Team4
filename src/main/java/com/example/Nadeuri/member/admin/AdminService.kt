package com.example.Nadeuri.member.admin

import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.dto.MemberDTO
import lombok.RequiredArgsConstructor
import lombok.extern.log4j.Log4j2
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
// 어드민 서비스
class AdminService(private val memberRepository: MemberRepository,private val modelMapper: ModelMapper) {

    // 멤버 전체 조회
    fun getAdminMemberAll(): List<MemberDTO> {
        val members: List<MemberEntity> = memberRepository.findAll()

        return members.map { modelMapper.map(it,MemberDTO::class.java)
            //modelMapper 사용하여 entity를 DTO로 변환
        }
    }

    // 멤버 조회
    fun getAdminMember(userId: String): MemberDTO {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("Account not found") }
        return MemberDTO(member)
    }

    // 멤버 수정
    fun updateAdminMember(userId: String, memberEntity: MemberEntity): MemberDTO {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("Account not found") }
        member.changeEmail(memberEntity.email)
        member.changeNickname(memberEntity.nickname)
        member.changeRole(memberEntity.role)
        member.changeBirthDate(memberEntity.birthDate)
        memberEntity.profileImage?.let { member.changeProfileImage(it) } // 이미지 변경시에만 업데이트
        //let 확장 함수를 사용하여 프로필이미지가 널이 아닐경우 프로필 이미지를 (it) 변경
        return MemberDTO(member)
    }

    // 멤버 삭제
    fun deleteAdminMember(userId: String) {
        val member: MemberEntity = memberRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("Account not found") }

        memberRepository.delete(member)
    }
}