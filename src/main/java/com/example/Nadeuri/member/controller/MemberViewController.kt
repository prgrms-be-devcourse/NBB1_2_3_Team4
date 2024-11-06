package com.example.Nadeuri.member.controller

import com.example.Nadeuri.member.MemberService
import com.example.Nadeuri.member.dto.MemberDTO
import com.example.Nadeuri.member.dto.request.MemberUpdateRequest
import com.example.Nadeuri.member.security.util.CookieUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/members")
class MemberViewController(private val memberService: MemberService) {

    @GetMapping("/login")
    fun login(): String {
        return "login" // login.html 파일을 반환
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): String {
        CookieUtil.deleteCookie(request, response, "accessToken")  // 액세스 토큰 쿠키 삭제
        CookieUtil.deleteCookie(request, response, "refreshToken") // 리프레시 토큰 쿠키 삭제
        return "redirect:/board/list"
    }

    @GetMapping("/sign-up")
    fun signUp(): String {
        return "sign-up" // sign-up.html 파일을 반환
    }


    // 회원정보 수정 페이지를 반환하는 메서드
    @GetMapping("/edit")
    fun showEditProfile(model: Model): String {
        // SecurityContextHolder에서 인증 정보를 가져옴
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val userId: String = authentication.name

        val memberDTO = MemberDTO(memberService.findByUserId(userId))
        model.addAttribute("member", memberDTO)
        return "members/edit"  // 회원정보 수정 페이지로 이동 (Thymeleaf 템플릿 등)
    }

    // 회원정보 수정 처리
    @PostMapping("/{userId}")
    fun updateMember(
        @PathVariable("userId") userId: String,
        @ModelAttribute memberUpdateRequest: MemberUpdateRequest,
        @RequestPart(value = "image", required = false) profileImage: MultipartFile?,
        authentication: Authentication,
    ): String {
        val dbUserId: String = authentication.name
        if (dbUserId != userId) {
            throw IllegalArgumentException("인증 사용자와 수정 대상이 일치하지 않습니다.")
        }

        memberService.updateMember(userId, memberUpdateRequest, profileImage)

        return "redirect:/board/list"
    }
}
