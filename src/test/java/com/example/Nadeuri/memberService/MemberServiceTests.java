package com.example.Nadeuri.memberService;

import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.exception.MemberException;
import com.example.Nadeuri.member.MemberRepository;
import com.example.Nadeuri.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class MemberServiceTests {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void readTest() {


    String userId = "user1";
    String password = "1111";

        Optional<MemberEntity> foundMember= memberRepository.findByUserId(userId);
        MemberEntity member = foundMember.orElseThrow(MemberException.BAD_CREDENTIALS::get);  //데이터베이스에 존재하지 않는 경우 MemberTaskException의 BAD_CREDENTIALS 예외를 발생시키고


//        if(passwordEncoder.matches(password, member.getPassword())) {   //password가 데이터베이스의 값과 일치하지 않는 경우
//            throw MemberException.BAD_CREDENTIALS.get();
//        }

        log.info(!passwordEncoder.matches(password, member.getPassword()));


    }



}
