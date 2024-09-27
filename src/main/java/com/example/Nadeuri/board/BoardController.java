package com.example.Nadeuri.board;

import com.example.Nadeuri.board.dto.request.BoardCreateRequest;
import com.example.Nadeuri.board.dto.request.BoardPageRequestDTO;
import com.example.Nadeuri.board.dto.request.BoardUpdateRequest;
import com.example.Nadeuri.board.dto.response.BoardDeleteResponse;
import com.example.Nadeuri.board.dto.response.BoardUpdateResponse;
import com.example.Nadeuri.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/boards")
@RequiredArgsConstructor
@RestController
@Log4j2
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시글 등록
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse> register(
            @RequestPart("request") @Valid final BoardCreateRequest request,
            @RequestPart("image") final MultipartFile multipartFile
    ) {
        boardService.register(request, multipartFile);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //게시글 상세 조회 (1개 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> read(@PathVariable("id") Long boardId) {
        log.info("readController() ---");
        return ResponseEntity.ok(ApiResponse.success(boardService.read(boardId)));
    }

    //게시글 전체 조회  --
    @GetMapping
    public ResponseEntity<ApiResponse> page(@Validated BoardPageRequestDTO boardPageRequestDTO) {
        log.info("pageController()---");
        return ResponseEntity.ok(ApiResponse.success(boardService.page(boardPageRequestDTO)));
    }

    //게시글 전체 조회 (검색) --
    @GetMapping("/search/{keyword}")
    public ResponseEntity<ApiResponse> pageSearch(@PathVariable("keyword") String keyword,
                                                  @Validated BoardPageRequestDTO boardPageRequestDTO) {
        log.info("pageSearchController()---");
        return ResponseEntity.ok(ApiResponse.success(boardService.pageSearch(keyword,
                boardPageRequestDTO)));
    }

    /**
     * 게시판 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("id") Long boardId,
            @RequestPart("request") @Valid final BoardUpdateRequest request,
            @RequestPart("image") final MultipartFile multipartFile
    ) {
        BoardEntity board = boardService.update(boardId, request, multipartFile);
        BoardUpdateResponse response = BoardUpdateResponse.from(board);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 게시판 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Long boardId
    ) {
        BoardEntity board = boardService.delete(boardId);
        BoardDeleteResponse response = BoardDeleteResponse.from(board);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
