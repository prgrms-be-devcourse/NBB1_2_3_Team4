package com.example.Nadeuri.member.repository;

import com.example.Nadeuri.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
