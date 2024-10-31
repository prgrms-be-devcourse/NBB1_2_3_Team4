package com.example.Nadeuri.member

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByUserId(userId: String): MemberEntity?
    fun existsByUserId(userId: String): Boolean
    fun findByEmail(email: String): MemberEntity?
}
