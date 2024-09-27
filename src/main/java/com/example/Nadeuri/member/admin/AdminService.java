package com.example.Nadeuri.member.admin;

import com.example.Nadeuri.member.MemberEntity;
import com.example.Nadeuri.member.MemberRepository;
import com.example.Nadeuri.member.MemberService;
import com.example.Nadeuri.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
//어드민 서비스
public class AdminService {

    private final MemberRepository memberRepository;

    //멤버 전체 조회
    public List<MemberDTO> getAdminMemberAll(){
        List<MemberEntity> member = memberRepository.findAll();

        return member.stream()
                .map(members -> new MemberDTO(
                        members.getUserId(),
                        members.getPassword(),
                        members.getName(),
                        members.getEmail(),
                        members.getRole(),
                        members.getNickname(),
                        members.getProfileImage(),
                        members.getBirthDate()))
                .collect(Collectors.toList());
    }

    //멤버 조회
    public MemberDTO getAdminMember(String userId){
        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return new MemberDTO(member);
    }

    //멤버 수정
    public MemberDTO updateAdminMember(String userId,MemberEntity memberEntity){
        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        member.changeEmail(memberEntity.getEmail());
        member.changeNickname(memberEntity.getNickname());
        member.changePassword(memberEntity.getPassword());
        member.changeRole(memberEntity.getRole());
        member.changeBirthDate(memberEntity.getBirthDate());
        if (!(memberEntity.getProfileImage() ==null))
        member.changeProfileImage(memberEntity.getProfileImage());// 이미지 변경시에만 업데이트

        return new MemberDTO(member);
    }

    //멤버 삭제
    public void deleteAdminMember(String userId) {
        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        memberRepository.delete(member);
    }

}
