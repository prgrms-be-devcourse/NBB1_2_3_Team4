//package com.example.Nadeuri.likes;
//
//import com.example.Nadeuri.common.response.ApiResponse;
//import com.example.Nadeuri.likes.dto.response.LikeDeleteResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping("/v1")
//@RequiredArgsConstructor
//@RestController
//@Log4j2
//public class LikeController {
//    private final LikeService likeService;
//
//    /**
//     * 좋아요 등록
//     */
//    @PostMapping("/{boardId}/likes")
//    public ResponseEntity<ApiResponse> like(
//            @PathVariable("boardId") Long boardId,
//            Authentication authentication
//    ) {
//        likeService.like(boardId, authentication.getName());
//        return ResponseEntity.ok(ApiResponse.success(null));
//    }
//
//    /**
//     * 좋아요 취소
//     */
//    @DeleteMapping("/{boardId}/likes")
//    public ResponseEntity<ApiResponse> unLike(
//            @PathVariable("boardId") Long boardId,
//            Authentication authentication
//    ) {
//        LikeEntity like = likeService.unLike(boardId, authentication.getName());
//        LikeDeleteResponse response = LikeDeleteResponse.from(like);
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }
//}
