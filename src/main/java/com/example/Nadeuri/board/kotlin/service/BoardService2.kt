package com.example.Nadeuri.board.kotlin.service

import com.example.Nadeuri.board.kotlin.entity.BoardEntity
import com.example.Nadeuri.board.kotlin.controller.dto.BoardCreateRequest2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardReadResponse2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardPageRequestDTO2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardUpdateRequest2
import com.example.Nadeuri.board.kotlin.exception.BoardException2
import com.example.Nadeuri.board.kotlin.repository.BoardRepository2
import com.example.Nadeuri.board.kotlin.repository.ImageRepository2
import com.example.Nadeuri.member.MemberEntity
import com.example.Nadeuri.member.MemberRepository
import com.example.Nadeuri.member.exception.MemberException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
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
    companion object {
        private val log: Logger = LogManager.getLogger(BoardService2::class.java)
    }

    @Value("\${file.local.upload.path}")
    private lateinit var uploadPath: String

    @Transactional
    fun register(
        request: BoardCreateRequest2,
        boardImage: MultipartFile?,
    ) {
        log.info("service()")
        val imageUrl = if (boardImage == null || boardImage.isEmpty) {
            "$uploadPath/defaultImage.png"
        } else {
            imageRepository2.upload(boardImage)
        }

        val memberId = request.memberId ?: throw IllegalArgumentException("memberId는 필수 값입니다.")
        val member: MemberEntity = retrieveMember(memberId)


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

    // 게시글 상세 조회 (1개 조회)
    fun read(boardId: Long): BoardReadResponse2 {
        val board: BoardEntity = retrieveBoard(boardId)
        log.info("read service()")
        if (board.deletedAt != null) throw BoardException2.NOT_FOUND.get()
        return BoardReadResponse2(board)
    }

    // 게시글 전체 조회
    fun page(boardPageRequestDTO: BoardPageRequestDTO2): Page<BoardReadResponse2> {
        try {
            log.info("page service()")
            val sort = Sort.by("id").ascending()
            val pageable = boardPageRequestDTO.getPageable(sort)
            val pageDTO = boardRepository2.pageDTO(pageable)
            log.info(pageDTO.totalElements)
            return pageDTO
        } catch (e: Exception) {
            throw BoardException2.NOT_FOUND.get()
        }
    }

    // 게시글 전체 조회 (검색) -- 제목, 작성자 검색
    fun pageSearch(keyword: String, boardPageRequestDTO: BoardPageRequestDTO2): Page<BoardReadResponse2> {
        return try {
            val sort = Sort.by("id").ascending()
            val pageable = boardPageRequestDTO.getPageable(sort)
            boardRepository2.pageSearch(keyword, pageable)
        } catch (e: Exception) {
            throw BoardException2.NOT_FOUND.get()
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