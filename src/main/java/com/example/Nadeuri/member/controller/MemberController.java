package com.example.Nadeuri.member.controller;

import com.example.Nadeuri.common.response.ApiResponse;
import com.example.Nadeuri.member.*;
import com.example.Nadeuri.member.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Log4j2
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @PostMapping("/sign-up") //회원가입
    public ResponseEntity<ApiResponse> signUp(@RequestPart("signUpDTO") SignupDTO signUpDTO, @RequestPart("image") final MultipartFile profileImage) {
        MemberDTO memberDTO = memberService.signUp(signUpDTO, profileImage);

        return ResponseEntity.ok(ApiResponse.success(null));
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByUserId(memberDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Id 입니다."));
        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        //사용자 정보 가져오기
        MemberDTO foundMemberDTO = memberService.read(memberDTO.getUserId(), memberDTO.getPassword());

        //토큰 생성
        Map<String, Object> payloadMap = foundMemberDTO.getPayload();
        String accessToken = jwtUtil.createToken(payloadMap, 5);    //60분 유효
        String refreshToken = jwtUtil.createToken(Map.of("userId", foundMemberDTO.getUserId()),
                60 * 24 * 7);                   //7일 유효

        log.info("--- accessToken : " + accessToken);
        log.info("--- refreshToken : " + refreshToken);

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }


}
