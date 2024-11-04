package com.example.Nadeuri.likes.service

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.board.kotlin.exception.BoardException2
import com.example.Nadeuri.board.kotlin.repository.BoardRepository2
import com.example.Nadeuri.likes.entity.LikeEntity
import com.example.Nadeuri.likes.exception.LikeException
import com.example.Nadeuri.likes.repository.LikeRepository
import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.exception.MemberException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val boardRepository: BoardRepository2,
    private val memberRepository: MemberRepository,
) {

    /**
     * 좋아요 등록
     */
    @Transactional
    fun like(principalName: String, boardId: Long)
    {
        val board = retrieveBoard(boardId)
        val member = retrieveMember(principalName)

        handleLike(member, board)
        board.increaseLikeCount()
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    fun unLike(principalName: String, boardId: Long): LikeEntity
    {
        val board = retrieveBoard(boardId)
        val member = retrieveMember(principalName)

        return findLikeByMemberAndBoard(member, board)?.let { like ->
            like.setLikeDeleted(true)
            board.decreaseLikeCount()
            like
        } ?: throw LikeException.LIKE_NOT_EXIST.get()
    }

    fun handleLike(member: MemberEntity, board: BoardEntity) {
        val existingLike = findLikeByMemberAndBoard(member, board)

        existingLike?.let { like ->
            // existingLike가 있는 경우
            restoreLike(like)
        } ?: run {
            // existingLike가 null일 경우
            createNewLike(member, board)
        }
    }

    fun restoreLike(like: LikeEntity) {
        if (like.isDeleted) {
            like.setLikeDeleted(false)
            likeRepository.save(like)
        }
    }

    fun createNewLike(member: MemberEntity, board: BoardEntity) {
        val newLike = LikeEntity.create(member, board);
        likeRepository.save(newLike);
    }

    fun findLikeByMemberAndBoard(member: MemberEntity, board: BoardEntity): LikeEntity? {
        return likeRepository.findByMemberAndBoard(member, board);
    }

    private fun retrieveMember(userId: String) =
        memberRepository.findByUserId(userId) ?: throw MemberException.NOT_FOUND.get()

    private fun retrieveBoard(boardId: Long) =
        boardRepository.findByIdOrNull(boardId) ?: throw BoardException2.NOT_FOUND.get()
}