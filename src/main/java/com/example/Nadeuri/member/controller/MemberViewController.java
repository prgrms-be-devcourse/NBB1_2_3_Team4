package com.example.Nadeuri.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberViewController {
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 파일을 반환
    }


    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up"; // sign-up.html 파일을 반환
    }
}
