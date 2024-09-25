package com.example.Nadeuri.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    // 댓글 생성
//    @PostMapping
//    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
//        CommentDTO createdComment = commentService.createComment(commentDTO);
//        return ResponseEntity.ok(createdComment);
//    }
//
//    // 게시글에 있는 댓글 조회
//    @GetMapping("/board/{boardId}")
//    public ResponseEntity<List<CommentDTO>> getCommentsByBoard(@PathVariable Long boardId) {
//        List<CommentDTO> comments = commentService.getCommentsByBoardId(boardId);
//        return ResponseEntity.ok(comments);
//    }
//
//    // 댓글 수정
//    @PutMapping("/{commentId}")
//    public ResponseEntity<CommentDTO> updateComment(
//            @PathVariable Long commentId,
//            @RequestParam String content,
//            @RequestParam Long memberId) {
//        CommentDTO updatedComment = commentService.updateComment(commentId, content, memberId);
//        return ResponseEntity.ok(updatedComment);
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(
//            @PathVariable Long commentId,
//            @RequestParam Long memberId) {
//        commentService.deleteComment(commentId, memberId);
//        return ResponseEntity.ok().build();
//    }
}
