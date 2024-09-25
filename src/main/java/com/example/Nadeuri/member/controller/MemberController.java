package com.example.Nadeuri.member.controller;

import com.example.Nadeuri.member.MemberDTO;
import com.example.Nadeuri.member.MemberService;
import com.example.Nadeuri.member.SignupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberDTO> signUp(@RequestBody SignupDTO signUpDTO) {
        MemberDTO memberDTO = memberService.signUp(signUpDTO);
        return ResponseEntity.ok(memberDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token, user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
