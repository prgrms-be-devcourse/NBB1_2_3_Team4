package com.example.Nadeuri.board.kotlin.service

import com.example.Nadeuri.board.BoardEntity
import com.example.Nadeuri.board.kotlin.controller.dto.BoardCreateRequest2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardUpdateRequest2
import com.example.Nadeuri.board.kotlin.exception.BoardException2
import com.example.Nadeuri.board.kotlin.repository.BoardRepository2
import com.example.Nadeuri.board.kotlin.repository.ImageRepository2
import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.exception.MemberException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime


@Transactional(readOnly = true)
@Service
class BoardService2(
    private val boardRepository2: BoardRepository2,
    private val memberRepository: MemberRepository,
    private val imageRepository2: ImageRepository2,
) {
    @Value("\${file.local.upload.path}")
    private lateinit var uploadPath: String

    @Transactional
    fun register(
        request: BoardCreateRequest2,
        boardImage: MultipartFile?,
    ) {

        val imageUrl = if (boardImage == null || boardImage.isEmpty) {
            "$uploadPath/defaultImage.png"
        } else {
            imageRepository2.upload(boardImage)
        }

        val member: MemberEntity = retrieveMember(request.memberId)

        try {
            val board = BoardEntity.create(
                member,
                request.boardTitle,
                request.boardContent,
                request.category2,
                imageUrl
            )
            boardRepository2.save(board)
        } catch (e: Exception) {
            throw BoardException2.NOT_REGISTERED.get()
        }
    }

    @Transactional
    fun update(
        principalName: String,
        boardId: Long,
        request: BoardUpdateRequest2,
        boardImage: MultipartFile?,
    ): BoardEntity {

        val board = retrieveBoard(boardId)

        board.validateWriter(principalName)

        try {
            val imageUrl = if (boardImage == null || boardImage.isEmpty) {
                "$uploadPath/defaultImage.png"
            } else {
                imageRepository2.upload(boardImage)
            }

            board.update(
                boardTitle = request.boardTitle,
                boardContent = request.boardContent,
                category2 = request.category2,
                imageUrl = imageUrl
            )
            return boardRepository2.save(board);
        } catch (e: Exception) {
            throw BoardException2.NOT_MODIFIED.get()
        }
    }

    @Transactional
    fun delete(
        principalName: String,
        boardId: Long,
    ): BoardEntity {

        val board = retrieveBoard(boardId)

        board.validateWriter(principalName)

        try {
            board.recordDeletion(LocalDateTime.now());
            return boardRepository2.save(board);
        } catch (e: Exception) {
            throw BoardException2.NOT_REMOVED.get();
        }
    }

    private fun retrieveMember(memberId: Long) =
        memberRepository.findByIdOrNull(memberId) ?: throw MemberException.NOT_FOUND.get()

    private fun retrieveBoard(boardId: Long) =
        boardRepository2.findByIdOrNull(boardId) ?: throw BoardException2.NOT_FOUND.get()
}