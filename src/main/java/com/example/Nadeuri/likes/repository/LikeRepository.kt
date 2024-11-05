package com.example.Nadeuri.likes.repository

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.likes.entity.LikeEntity
import com.example.Nadeuri.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<LikeEntity, Long> {
    fun findByMemberAndBoard(member: MemberEntity, board: BoardEntity): LikeEntity?
}