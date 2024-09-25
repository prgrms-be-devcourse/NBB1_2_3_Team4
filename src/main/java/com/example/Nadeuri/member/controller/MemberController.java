package com.example.Nadeuri.member.controller;

import com.example.Nadeuri.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/test")
    public String test() {
        return "ok";
    }
}
