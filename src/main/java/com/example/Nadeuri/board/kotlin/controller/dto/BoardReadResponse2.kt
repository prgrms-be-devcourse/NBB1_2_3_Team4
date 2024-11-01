package com.example.Nadeuri.board.kotlin.controller.dto

import com.example.Nadeuri.board.kotlin.domain.Category2
import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.comment.entity.CommentEntity
import java.time.LocalDateTime

data class BoardReadResponse2(
    var id: Long? = null,
    var member: String? = null, // 작성자
    var boardTitle: String? = null,
    var boardContent: String? = null,
    var category: Category2? = null,
    var imageUrl: String? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null,
    var comments: List<String>? = null // 댓글
){
    constructor(boardEntity: BoardEntity) : this() {
        this.id = boardEntity.id
        this.member = boardEntity.member.userId
        this.boardTitle = boardEntity.boardTitle
        this.boardContent = boardEntity.boardContent
        this.category = boardEntity.category2
        this.imageUrl = boardEntity.imageUrl
        this.createdAt = boardEntity.createdAt
        this.updatedAt = boardEntity.updatedAt
        this.deletedAt = boardEntity.deletedAt
        this.comments = boardEntity.comments.map(CommentEntity::content)
    }
}