package com.example.Nadeuri.board.kotlin.controller

import com.example.Nadeuri.board.kotlin.controller.dto.BoardCreateRequest2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardPageRequestDTO2
import com.example.Nadeuri.board.kotlin.controller.dto.BoardReadResponse2
import com.example.Nadeuri.board.kotlin.service.BoardService2
import com.example.Nadeuri.member.MemberService
import org.springframework.data.domain.Page
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/board")
class BoardViewController2(
    private val boardService: BoardService2,
    private val memberService: MemberService
) {

    /**
     * 게시글 목록 페이지
     */
    @GetMapping("/list")
    fun listBoardPage(@Validated boardPageRequestDTO: BoardPageRequestDTO2, model: Model): String {
        // 페이지 요청에 따라 게시글 목록을 가져옴
        val boards: Page<BoardReadResponse2> = boardService.page(boardPageRequestDTO)
        model.addAttribute("boards", boards.content)
        model.addAttribute("page", boards)
        return "board/list" // list.html로 이동
    }

    //게시글 상세 조회
    @GetMapping("/{id}")
    fun read(@PathVariable("id") boardId: Long, model: Model): String {
        val board: BoardReadResponse2? = boardService.read(boardId) // 게시글 조회
        if (board != null) {
            model.addAttribute("article", board) // 모델에 게시글 추가
        } else {
            model.addAttribute("error", "게시글을 찾을 수 없습니다.") // 게시글이 없을 경우 에러 메시지 추가
        }

        return "board/article" // article.html로 이동
    }

    @GetMapping("/create")
    fun createForm(): String {
        return "board/create" // 게시글 작성 HTML 파일 이름
    }

    // 게시글 작성 처리
    @PostMapping("/create")
    fun createBoard(
        @ModelAttribute request: BoardCreateRequest2,
        @RequestParam image: MultipartFile,
        authentication: Authentication
    ): String {
        // Authentication 객체에서 username 추출
        val userId: String = authentication.name // username 가져오기
        val memberId: Long = memberService.findByUserId(userId).memberNo // username으로 memberId 조회
        val updatedRequest = BoardCreateRequest2(
            memberId,
            request.boardTitle,
            request.boardContent,
            request.category2
        )

        // 게시글 등록 처리
        boardService.register(updatedRequest, image)
        return "redirect:/boards/list" // 게시글 목록 페이지로 리다이렉트
    }
}