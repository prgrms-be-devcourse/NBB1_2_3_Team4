package com.example.Nadeuri.member

import com.example.Nadeuri.board.kotlin.repository.ImageRepository2
import com.example.Nadeuri.member.dto.MemberDTO
import com.example.Nadeuri.member.dto.request.MemberUpdateRequest
import com.example.Nadeuri.member.dto.request.SignupDTO
import com.example.Nadeuri.member.exception.MemberException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val imageRepository: ImageRepository2,
    private val modelMapper: ModelMapper,
    @Value("\${file.local.upload.path}") private val uploadPath: String
) {

    fun read(userId: String, password: String): MemberDTO {
        val foundMember = memberRepository.findByUserId(userId)
        val member = foundMember ?: throw MemberException.BAD_CREDENTIALS.get()

        if (!passwordEncoder.matches(password, member.password)) {
            throw MemberException.BAD_CREDENTIALS.get() // 비밀번호가 일치하지 않는 경우 예외 발생
        }

        return MemberDTO(member)
    }

    fun read(userId: String): MemberDTO {
        val foundMember = memberRepository.findByUserId(userId)
        val member = foundMember ?: throw MemberException.BAD_CREDENTIALS.get()

        return MemberDTO(member)// 엔티티를 DTO 객체로 반환
    }

    // 회원 가입
    fun signUp(signupDTO: SignupDTO, profileImage: MultipartFile?) {
        if (memberRepository.existsByUserId(signupDTO.userId)) {
            throw MemberException.DUPLICATE.get()
        }

        val imageUrl: String = if (profileImage == null || profileImage.isEmpty) {
            "$uploadPath/defaultImage.png"
        } else {
            imageRepository.upload(profileImage)
        }

        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(signupDTO.password)
        val role = Role.USER

        memberRepository.save(signupDTO.toEntity(encodedPassword, role, imageUrl))
    }

    fun updateMember(userId: String, request: MemberUpdateRequest, profileImage: MultipartFile?) {
        val memberEntity = memberRepository.findByUserId(userId)
            ?: throw MemberException.NOT_FOUND.get()

        val imageUrl = if (profileImage != null && !profileImage.isEmpty) {
            imageRepository.upload(profileImage)
        } else {
            "$uploadPath/defaultImage.png"
        }

        memberEntity.changeProfileImage(imageUrl)

        request.role?.let { memberEntity.changeRole(it) }  // request.role 이 null이 아닐 경우, let 실행
//        request.password?.takeIf{it.isNotEmpty()}?.let { memberEntity.changePassword(passwordEncoder.encode(it)) }
        request.email?.takeIf{it.isNotEmpty()}?.let { memberEntity.changeEmail(it) }
        request.name?.takeIf{it.isNotEmpty()}?.let { memberEntity.changeName(it) }
        request.nickname?.takeIf{it.isNotEmpty()}?.let { memberEntity.changeNickname(it) }

        memberRepository.save(memberEntity)
    }

    fun deleteMember(userId: String) {
        val memberEntity = memberRepository.findByUserId(userId)
            ?: throw MemberException.NOT_FOUND.get()

        memberRepository.delete(memberEntity)
    }

    fun findByUserId(userId: String): MemberEntity {
        return memberRepository.findByUserId(userId)?: throw IllegalArgumentException("UserId not found: $userId")
    }

}
