//package com.example.Nadeuri.board;
//
//import com.example.Nadeuri.board.dto.request.BoardPageRequestDTO;
//import com.example.Nadeuri.common.response.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////@RequestMapping("/v1/boards")
////@RequiredArgsConstructor
////@RestController
////@Log4j2
//public class BoardController {
////    private final BoardService boardService;
//
////    //게시글 상세 조회 (1개 조회)
////    @GetMapping("/{id}")
////    public ResponseEntity<ApiResponse> read(@PathVariable("id") Long boardId) {
////        return ResponseEntity.ok(ApiResponse.success(boardService.read(boardId)));
////    }
////
////    //게시글 전체 조회  --
////    @GetMapping
////    public ResponseEntity<ApiResponse> page(@Validated BoardPageRequestDTO boardPageRequestDTO) {
////        return ResponseEntity.ok(ApiResponse.success(boardService.page(boardPageRequestDTO)));
////    }
////
////    //게시글 전체 조회 (검색) --
////    @GetMapping("/search/{keyword}")
////    public ResponseEntity<ApiResponse> pageSearch(@PathVariable("keyword") String keyword,
////                                                  @Validated BoardPageRequestDTO boardPageRequestDTO) {
////        return ResponseEntity.ok(ApiResponse.success(boardService.pageSearch(keyword,
////                boardPageRequestDTO)));
////    }
//
//}
