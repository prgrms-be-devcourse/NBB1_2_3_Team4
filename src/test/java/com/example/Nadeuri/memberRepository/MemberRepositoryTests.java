package com.example.Nadeuri.memberRepository;

import com.example.Nadeuri.member.domain.Member;
import com.example.Nadeuri.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {    //테스트 데이터 100개 등록
        //user1 ~ user80 role USER로, user81 ~ user100   "  ADMIN으로 지정
        IntStream.rangeClosed(1, 100).forEach(i -> {
            //GIVEN - Todo 엔티티 객체 생성
            Member member = Member.builder().userId("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .email("user" + i + "@aaa.com")
                    .role(i <= 80 ? "USER" : "ADMIN")
                    .build();

            //WHEN - 엔티티 저장 => saved 메서드가 insert쿼리 역할
            Member savedMember = memberRepository.save(member);

            assertNotNull(savedMember);   //THEN - savedTodo가 Null이 아닐 것
            if(i <= 80)   assertEquals(savedMember.getRole(), "USER");//user1 ~ user80은 USER role
            else          assertEquals(savedMember.getRole(), "ADMIN");//user81 ~ user100은 ADMIN role과 같을 것
        });
    }

    @Test  //SELECT 테스트
    public void testFindByUserId() {
        //GIVEN  //@Id 타입의 값으로 엔티티 조회
        String userId = "user1";

        //WHEN
        Optional<Member> foundMember = memberRepository.findByUserId(userId);   //Optional<> => 값이 있을 수도 없을 수도 있다는 뜻

        //THEN
        assertNotNull(foundMember);
        assertEquals(userId, foundMember.get().getUserId());

        log.info("foundMember : " + foundMember);
        log.info("userId : " + foundMember.get().getUserId());

        /////////////////////////////////////////////////////
//        try {
//            userId = "user111111111";
//            foundMember = memberRepository.findByUserId(userId);
//            foundMember.orElseThrow(MemberException.NOT_FOUND::get);
//        } catch(MemberTaskException e) {
//            assertEquals(404, e.getCode());
//            log.info("e : " + e);
//        }
    }
}
