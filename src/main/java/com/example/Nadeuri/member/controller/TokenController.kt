package com.example.Nadeuri.member.controller

import com.example.Nadeuri.member.MemberService
import com.example.Nadeuri.member.security.util.JWTUtil
import io.jsonwebtoken.ExpiredJwtException
import lombok.RequiredArgsConstructor
import lombok.extern.log4j.Log4j2
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/token")
@Log4j2
class TokenController(
    private val memberService: MemberService,
    private val jwtUtil: JWTUtil
) {

    companion object {
        private val log: Logger = LogManager.getLogger(MemberController::class.java)
    }

    private fun handleException(message: String, status: Int): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(status).body(mapOf("error" to message))
    }

    // 액세스 토큰과 리프레시 토큰을 검증하여 새 토큰 발급 혹은 현재 토큰 반환
    @PostMapping("/refresh")
    fun refresh(
        @RequestHeader("Authorization") headerAuth: String?,
        @RequestParam("refreshToken") refreshToken: String,
        @RequestParam("userId") userId: String
    ): ResponseEntity<Map<String, String>> {
        log.info("--- refreshOrVerify()")

        // 기본적인 토큰 값 검증
        if (headerAuth.isNullOrEmpty() || !headerAuth.startsWith("Bearer ")) {
            return handleException("액세스 토큰이 없습니다.", 400)
        }
        if (refreshToken.isEmpty() || userId.isEmpty()) {
            return handleException("리프레시 토큰 혹은 아이디가 없습니다.", 400)
        }

        val accessToken = headerAuth.substring(7)

        return try {
            val claims = jwtUtil.validateToken(accessToken)
            if (claims["userId"] != userId) {
                return handleException("INVALID ACCESS TOKEN userId", 400)
            }
            log.info("액세스 토큰 유효 - 기존 토큰 반환")
            ResponseEntity.ok(mapOf("accessToken" to accessToken, "refreshToken" to refreshToken, "userId" to userId))
        } catch (e: ExpiredJwtException) {
            log.info("액세스 토큰 만료 - 리프레시 토큰 검증")
            validateAndRefreshTokens(refreshToken, userId)
        } catch (e: Exception) {
            log.error("그 외 모든 처리 예외", e)
            handleException("그 외 모든 처리 예외: ${e.message}", 400)
        }
    }

    // 리프레시 토큰을 검증하고 새로운 토큰 생성
    private fun validateAndRefreshTokens(refreshToken: String, userId: String): ResponseEntity<Map<String, String>> {
        return try {
            val claims = jwtUtil.validateToken(refreshToken)
            if (claims["userId"] != userId) {
                return handleException("INVALID REFRESH TOKEN userId", 400)
            }

            log.info("리프레시 토큰 유효 - 새 토큰 생성")
            val foundMemberDTO = memberService.read(userId)
            val payloadMap = foundMemberDTO.getPayload()
            val newAccessToken = jwtUtil.createToken(payloadMap, 10)
            val newRefreshToken = jwtUtil.createToken(mapOf("userId" to userId), 3)

            ResponseEntity.ok(mapOf("accessToken" to newAccessToken, "refreshToken" to newRefreshToken, "userId" to userId))
        } catch (ee: ExpiredJwtException) {
            log.info("리프레시 토큰 만료 기간 경과")
            handleException("Refresh token has expired: ${ee.message}", 400)
        }
    }
}


