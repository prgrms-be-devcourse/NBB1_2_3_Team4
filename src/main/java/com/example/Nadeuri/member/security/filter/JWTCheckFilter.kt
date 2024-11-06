package com.example.Nadeuri.member.security.filter

import com.example.Nadeuri.member.Role
import com.example.Nadeuri.member.controller.MemberController
import com.example.Nadeuri.member.security.auth.CustomUserPrincipal
import com.example.Nadeuri.member.security.util.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class JWTCheckFilter(
    private val jwtUtil: JWTUtil
) : OncePerRequestFilter() {

    companion object {
        private val log: Logger = LogManager.getLogger(MemberController::class.java)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        log.info("--- shouldNotFilter()")
        log.info("--- requestURI : ${request.requestURI}")

        return when {
            request.requestURI.startsWith("/v1/token/") -> true
            request.requestURI.startsWith("/v1/members/sign-up") -> true
            request.requestURI.startsWith("/v1/members/login") -> true
            request.requestURI.startsWith("/members/sign-up") -> true
            request.requestURI.startsWith("/members/login") -> true
            request.requestURI.startsWith("/board/list") -> true
            request.requestURI.startsWith("/img/") -> true

            request.requestURI == "/v2/boards" -> true
            request.requestURI == "/boards/" -> true
            request.requestURI.matches(Regex("/board/\\d+")) -> true // 게시글 상세 조회 요청 추가
            else -> false
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("--- doFilterInternal()")
        log.info("--- requestURI : ${request.requestURI}")

        // 쿠키에서 토큰 값을 가져와야 하므로 쿠키에서 액세스 토큰 검색
        var accessToken: String? = null
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == "accessToken") {
                    accessToken = cookie.value
                    break
                }
            }
        }

        log.info("--- accessToken : $accessToken")

        // 액세스 토큰이 없으면 403 예외 발생
        if (accessToken == null) {
            handleException(response, Exception("ACCESS TOKEN NOT FOUND"))
            return
        }

        // 토큰 유효성 검증
        try {
            val claims = jwtUtil.validateToken(accessToken)
            log.info("--- 토큰 유효성 검증 완료 ---")

            // SecurityContext 처리
            val userId = claims["userId"].toString()
            val roleString = claims["role"].toString()
            val role = Role.valueOf(roleString)

            val authorities = role.getAuthorities()

            authorities.forEach { authority -> log.info("beforeAuthority: ${authority.name}") }

            // Spring Security의 권한 객체로 변환
            val grantedAuthorities = authorities.map { r -> SimpleGrantedAuthority("ROLE_${r.name}") }

            grantedAuthorities.forEach { authority -> log.info("Granted Authority: ${authority.authority}") }

            // 토큰을 이용하여 인증된 정보 저장
            val authToken = UsernamePasswordAuthenticationToken(
                CustomUserPrincipal(userId), null, grantedAuthorities
            )

            // SecurityContext에 인증/인가 정보 저장
            SecurityContextHolder.getContext().authentication = authToken

            filterChain.doFilter(request, response) // 검증 결과 문제가 없는 경우
        } catch (e: Exception) {
            handleException(response, e)
        }
    }

    private fun handleException(response: HttpServletResponse, e: Exception) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = "application/json"
        response.writer.println("{\"error\": \"${e.message}\"}")
    }
}
