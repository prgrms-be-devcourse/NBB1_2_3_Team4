package com.example.Nadeuri.member.controller;

import com.example.Nadeuri.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class OAuthMemberViewController {

    private final MemberService blogService;


    @GetMapping("/oauthlogin")
    public String login() {
        return "oauthLogin"; // oauthLogin.html 파일을 반환
    }


    @GetMapping("/success")
    public String success(Model model) {
        // 필요한 데이터 추가
        return "success"; // success.html로 이동
    }
}
