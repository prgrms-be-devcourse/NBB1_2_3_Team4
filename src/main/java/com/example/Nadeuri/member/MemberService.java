package com.example.Nadeuri.member;

import com.example.Nadeuri.member.exception.DuplicateMemberException;
import com.example.Nadeuri.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO read(String userId, String password) {
        //userId와 password를 매개변수로 넘겨 받아 -------------------------------
        Optional<MemberEntity> foundMember= memberRepository.findByUserId(userId);
        MemberEntity member = foundMember.orElseThrow(MemberException.BAD_CREDENTIALS::get);  //데이터베이스에 존재하지 않는 경우 MemberTaskException의 BAD_CREDENTIALS 예외를 발생시키고

        if(!passwordEncoder.matches(password, member.getPassword())) {   //password가 데이터베이스의 값과 일치하지 않는 경우
            throw MemberException.BAD_CREDENTIALS.get();
        }

        return new MemberDTO(member);   //그렇지 않으면 넘겨받은 엔티티를 DTO 객체로 반환
    }

    public MemberDTO read(String userId) {
        //userId를 매개변수로 넘겨 받아 -------------------------------
        Optional<MemberEntity> foundMember= memberRepository.findByUserId(userId);
        MemberEntity member = foundMember.orElseThrow(MemberException.BAD_CREDENTIALS::get);  //데이터베이스에 존재하지 않는 경우 MemberTaskException의 BAD_CREDENTIALS 예외를 발생시키고

        return new MemberDTO(member);   //그렇지 않으면 넘겨받은 엔티티를 DTO 객체로 반환
    }

    //회원 가입
    public MemberDTO signUp(SignupDTO signupDTO) {
        if (memberRepository.existsByUserId(signupDTO.getUserId())) {
            throw new DuplicateMemberException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signupDTO.getPassword());
        String role = "USER";

        return new MemberDTO(memberRepository.save(signupDTO.toEntity(encodedPassword, role)));
    }

}
