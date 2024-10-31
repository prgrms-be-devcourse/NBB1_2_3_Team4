package com.example.Nadeuri.comment.repository

import com.example.Nadeuri.comment.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {

    // 특정 게시글에 달린 모든 댓글 조회
    fun findByBoardId(boardId: Long): List<CommentEntity>

    // 특정 유저의 모든 댓글 조회 - userId
    fun findByMemberUserId(userId: String): List<CommentEntity>
}

/*
 댓글 관련 JPA Repository
 */