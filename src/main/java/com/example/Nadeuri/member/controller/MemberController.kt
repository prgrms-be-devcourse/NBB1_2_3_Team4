package com.example.Nadeuri.member.controller

import com.example.Nadeuri.common.response.ApiResponse
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.MemberService
import com.example.Nadeuri.member.dto.MemberDTO
import com.example.Nadeuri.member.dto.request.LoginDTO
import com.example.Nadeuri.member.dto.request.MemberUpdateRequest
import com.example.Nadeuri.member.dto.request.SignupDTO
import com.example.Nadeuri.member.exception.MemberException
import com.example.Nadeuri.member.security.util.CookieUtil
import com.example.Nadeuri.member.security.util.JWTUtil
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JWTUtil,
    private val modelMapper: ModelMapper
) {

    companion object {
        private val log: Logger = LogManager.getLogger(MemberController::class.java)
    }

    @PostMapping("/sign-up") // 회원가입
    fun signUp(
        @Valid @RequestPart("signUpDTO") signUpDTO: SignupDTO,
        @RequestPart(value = "image", required = false) profileImage: MultipartFile?
    ): ResponseEntity<ApiResponse<Any?>> {
        memberService.signUp(signUpDTO, profileImage)
        return ResponseEntity.ok(ApiResponse.success(null))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginDTO: LoginDTO,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        val member = memberRepository.findByUserId(loginDTO.userId)?: throw IllegalArgumentException("가입되지 않은 Id 입니다.")

        if (!passwordEncoder.matches(loginDTO.password, member.password)) {
            throw IllegalArgumentException("잘못된 비밀번호입니다.")
        }

        // 사용자 정보 가져오기
        val foundMemberDTO = memberService.read(loginDTO.userId, loginDTO.password)

        // 토큰 생성
        val payloadMap = foundMemberDTO.getPayload()
        val accessToken = jwtUtil.createToken(payloadMap, 1)
        val refreshToken = jwtUtil.createToken(mapOf("userId" to foundMemberDTO.userId), 1)

        // 쿠키에 토큰 저장
        CookieUtil.addCookie(response, "accessToken", accessToken, 3600)
        CookieUtil.addCookie(response, "refreshToken", refreshToken, 604800)

        log.info("--- accessToken : $accessToken")
        log.info("--- refreshToken : $refreshToken")

        return ResponseEntity.ok(mapOf("accessToken" to accessToken, "refreshToken" to refreshToken))
    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable("userId") userId: String,
        @RequestPart("memberUpdateRequest") memberUpdateRequest: MemberUpdateRequest,
        @RequestPart(value = "image", required = false) profileImage: MultipartFile?,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Any?>> {
        val dbUserId = authentication.name
        log.info(dbUserId)

        if (authentication.name != userId || dbUserId != userId) { // 인증 사용자와 DTO의 사용자가 불일치
            throw MemberException.NOT_MATCHED_USER.get()
        }

        memberService.updateMember(userId, memberUpdateRequest, profileImage)
        log.info(memberUpdateRequest.role)
        return ResponseEntity.ok(ApiResponse.success(null))
    }

    @DeleteMapping("/{userId}")
    fun delete(
        @PathVariable("userId") userId: String,
        authentication: Authentication
    ): ResponseEntity<ApiResponse<Any?>> {
        val dbUserId = authentication.name
        log.info(dbUserId)

        if (authentication.name != userId || dbUserId != userId) {
            throw MemberException.NOT_MATCHED_USER.get()
        }

        memberService.deleteMember(userId)
        return ResponseEntity.ok(ApiResponse.success(null))
    }

    @GetMapping("/me")
    fun getCurrentUser(authentication: Authentication): ResponseEntity<ApiResponse<MemberDTO>> {
        val userId = authentication.name // 인증된 사용자 ID 가져오기
        val memberEntity = memberRepository.findByUserId(userId)
        val memberDTO = MemberDTO(memberEntity ?: throw MemberException.NOT_FOUND.get()) // 사용자 정보 조회
        return ResponseEntity.ok(ApiResponse.success(memberDTO)) // 사용자 정보를 포함한 ApiResponse 반환
    }
}
