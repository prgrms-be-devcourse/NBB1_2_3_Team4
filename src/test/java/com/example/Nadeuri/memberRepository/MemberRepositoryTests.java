package com.example.Nadeuri.memberRepository;


import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import com.example.Nadeuri.member.Role;
import com.example.Nadeuri.member.exception.MemberException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

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
            MemberEntity member = MemberEntity.builder().userId("user" + i)
                    .password(passwordEncoder.encode("1111"))
                    .email("user" + i + "@aaa.com")
                    .role(i <= 80 ? Role.USER : Role.ADMIN)
                    .build();

            //WHEN - 엔티티 저장 => saved 메서드가 insert쿼리 역할
            MemberEntity savedMember = memberRepository.save(member);

            assertNotNull(savedMember);   //THEN - savedTodo가 Null이 아닐 것
            if(i <= 80)   assertEquals(savedMember.getRole(), "USER");//user1 ~ user80은 USER role
            else          assertEquals(savedMember.getRole(), "ADMIN");//user81 ~ user100은 ADMIN role과 같을 것
        });
    }

    @Test  //SELECT 테스트
    @Transactional
    public void testFindByUserId() {
        //GIVEN  //@Id 타입의 값으로 엔티티 조회
        String userId = "user1";

        //WHEN
        Optional<MemberEntity> foundMember = memberRepository.findByUserId(userId);   //Optional<> => 값이 있을 수도 없을 수도 있다는 뜻

        //THEN
        assertNotNull(foundMember);
        assertEquals(userId, foundMember.get().getUserId());

        log.info("foundMember : " + foundMember);
        log.info("userId : " + foundMember.get().getUserId());


    }

    @Test  //UPDATE 테스트 - 트랜잭션 O
    @Transactional
    @Commit  //autoCommit이 아니기 때문에 붙임
    public void testUpdate() {
        //GIVEN  //@Id 타입의 값으로 엔티티 조회
        String userId = "user3";
        Role role = Role.ADMIN;
        //WHEN
        //사용자를 데이터베이스에서 조회
        Optional<MemberEntity> foundMember = memberRepository.findByUserId(userId);
        //조회 결과가 없으면 MemberTaskException으로 NOT_FOUND 예외를 발생시키고
        foundMember.orElseThrow(MemberException.NOT_FOUND::get);

        foundMember.get().changeRole(role);

        assertEquals("ADMIN", foundMember.get().getRole());
//        assertEquals("2222", passwordEncoder.encode(foundMember.get().getMpw()));
    }

}
